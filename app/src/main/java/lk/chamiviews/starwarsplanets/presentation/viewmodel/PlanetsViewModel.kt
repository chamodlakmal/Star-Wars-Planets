package lk.chamiviews.starwarsplanets.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lk.chamiviews.starwarsplanets.data.mapper.toPlanet
import lk.chamiviews.starwarsplanets.data.model.Planet
import lk.chamiviews.starwarsplanets.domain.usecase.GetNextPageUseCase
import lk.chamiviews.starwarsplanets.domain.usecase.GetPlanetsUseCase
import lk.chamiviews.starwarsplanets.presentation.event.PlanetEvent
import lk.chamiviews.starwarsplanets.presentation.state.PlanetsState
import lk.chamiviews.starwarsplanets.utils.NoMorePagesException
import lk.chamiviews.starwarsplanets.utils.NoNetworkException
import lk.chamiviews.starwarsplanets.utils.RemoteDataSourceException
import javax.inject.Inject

@HiltViewModel
class PlanetsViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase,
    private val getNextPageUseCase: GetNextPageUseCase
) : ViewModel() {

    private val _planetsState = MutableStateFlow<PlanetsState>(PlanetsState.Loading)
    val planetsState = _planetsState.asStateFlow()

    private val _selectedPlanet = MutableStateFlow<Planet?>(null)
    val selectedPlanet: StateFlow<Planet?> = _selectedPlanet.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private var nextPageUrl: String? = null

    init {
        fetchPlanets()
    }

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
        viewModelScope.launch {
            _planetsState.update {
                PlanetsState.Loading
            }
            val result = getPlanetsUseCase()
            result.onSuccess { response ->
                _planetsState.update {
                    PlanetsState.Success(response.results.map { it.toPlanet() })
                }
                nextPageUrl = response.next
            }.onFailure { error ->
                handleError(error)
            }
        }
    }


    private fun loadMorePlanets() {
        if (!_isLoadingMore.value && nextPageUrl != null) {
            _isLoadingMore.value = true
            viewModelScope.launch {
                val result = getNextPageUseCase(nextPageUrl!!)
                result.onSuccess { response ->
                    val planets = response.results.map { it.toPlanet() }
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

    private fun handleError(error: Throwable) {
        _planetsState.update {
            when (error) {
                is NoNetworkException -> PlanetsState.Error(
                    error.message ?: "No network connection available"
                )

                is NoMorePagesException -> PlanetsState.Error("No more pages available")
                is RemoteDataSourceException -> PlanetsState.Error(error.message ?: "Unknown error")
                else -> PlanetsState.Error("Unknown error")
            }
        }
    }
}