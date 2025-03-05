package lk.chamiviews.starwarsplanets.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lk.chamiviews.starwarsplanets.domain.usecase.GetNextPageUseCase
import lk.chamiviews.starwarsplanets.domain.usecase.GetPlanetsUseCase
import lk.chamiviews.starwarsplanets.presentation.event.PlanetEvent
import lk.chamiviews.starwarsplanets.presentation.state.PlanetsState
import lk.chamiviews.starwarsplanets.utils.NoNetworkException
import lk.chamiviews.starwarsplanets.utils.RemoteDataSourceException
import javax.inject.Inject

// ViewModel responsible for managing the state of planets in the app
@HiltViewModel
class PlanetsViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase,
    private val getNextPageUseCase: GetNextPageUseCase
) : ViewModel() {

    // Mutable state to hold the planets' UI state (e.g., loading, success, error)
    private val _planetsState = MutableStateFlow<PlanetsState>(PlanetsState.Loading)
    val planetsState = _planetsState.asStateFlow()

    // Mutable state to manage loading status of next page
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    // Holds the URL for the next page of results
    private var nextPageUrl: String? = null

    // CoroutineExceptionHandler to handle exceptions in coroutines
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleError(exception)
    }

    // Initialization: fetch the initial list of planets when the ViewModel is created
    init {
        fetchPlanets()
    }

    // This function listens for events (e.g., load more planets, fetch planets)
    fun planetEvent(event: PlanetEvent) {
        when (event) {
            PlanetEvent.LoadMorePlanets -> {
                loadMorePlanets()
            }

            PlanetEvent.FetchPlanets -> {
                fetchPlanets()
            }
        }
    }

    private fun fetchPlanets() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _planetsState.update {
                PlanetsState.Loading
            }
            getPlanetsUseCase().collect { result ->
                result.onSuccess { response ->
                    _planetsState.update {
                        PlanetsState.Success(response.results)
                    }
                    nextPageUrl = response.next
                }.onFailure { error ->
                    handleError(error)
                }
            }

        }
    }


    private fun loadMorePlanets() {
        if (!_isLoadingMore.value && nextPageUrl != null) {
            _isLoadingMore.value = true
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                getNextPageUseCase(nextPageUrl!!).collect { result ->
                    result.onSuccess { response ->
                        val planets = response.results
                        val currentPlanets =
                            (_planetsState.value as? PlanetsState.Success)?.planets.orEmpty()
                        _planetsState.update {
                            PlanetsState.Success(currentPlanets + planets)
                        }
                        nextPageUrl = response.next
                    }.onFailure { error ->
                        handleError(error)
                    }
                    _isLoadingMore.value = false
                }

            }
        }
    }

    private fun handleError(error: Throwable) {
        _planetsState.update {
            when (error) {
                is NoNetworkException -> PlanetsState.Error(
                    error.message ?: "No network connection available"
                )

                is RemoteDataSourceException -> PlanetsState.Error(error.localizedMessage ?: "Unknown error")
                else -> PlanetsState.Error(error.localizedMessage ?: "")
            }
        }
    }
}