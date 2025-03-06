package lk.chamiviews.starwarsplanets.presentation.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import lk.chamiviews.starwarsplanets.domain.model.Planet
import lk.chamiviews.starwarsplanets.domain.model.PlanetPageData
import lk.chamiviews.starwarsplanets.domain.usecase.GetNextPageUseCase
import lk.chamiviews.starwarsplanets.domain.usecase.GetPlanetsUseCase
import lk.chamiviews.starwarsplanets.presentation.event.PlanetEvent
import lk.chamiviews.starwarsplanets.presentation.state.PlanetsState
import lk.chamiviews.starwarsplanets.utils.NoNetworkException
import lk.chamiviews.starwarsplanets.utils.RemoteDataSourceException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@FlowPreview
class PlanetsViewModelTest {

    private lateinit var planetsViewModel: PlanetsViewModel
    private lateinit var getPlanetsUseCase: GetPlanetsUseCase
    private lateinit var getNextPageUseCase: GetNextPageUseCase

    private val dispatcher = StandardTestDispatcher()

    private val planetResponse = PlanetPageData(
        next = "nextPageUrl",
        results = listOf(
            Planet(
                name = "Earth",
                climate = "Tropical",
                orbitalPeriod = "365",
                gravity = "9.8"
            )
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        // Mocking the use cases
        getPlanetsUseCase = mock()
        getNextPageUseCase = mock()

        whenever(getPlanetsUseCase()).thenReturn(flowOf(Result.success(planetResponse)))

        // Initialize the ViewModel
        planetsViewModel = PlanetsViewModel(getPlanetsUseCase, getNextPageUseCase)

    }

    @Test
    fun `getPlanets should emit success state with planet data on successful fetch`() =
        runTest {
            // Arrange: You can mock the response if necessary
            val planetResponse = PlanetPageData(
                next = null,
                results = listOf(
                    Planet(
                        name = "Earth",
                        climate = "Temperate",
                        orbitalPeriod = "365",
                        gravity = "9.8"
                    )
                )
            )
            whenever(getPlanetsUseCase()).thenReturn(flowOf(Result.success(planetResponse)))

            // Set up a listener to observe the planetsState
            val job = launch {
                planetsViewModel.planetsState.collectLatest { state ->
                    // Assert the state when it changes
                    if (state is PlanetsState.Success) {
                        assertEquals(planetResponse.results, state.planets)
                    }
                }
            }

            // Act: Trigger the event to fetch planets
            planetsViewModel.planetEvent(PlanetEvent.FetchPlanets)

            // Wait for all coroutines to complete
            advanceUntilIdle()

            // Clean up
            job.cancel() // Cancel the collection job to stop observing state
        }


    @Test
    fun `getPlanets should emit error state on failure to fetch planets`() = runTest {
        // Arrange
        val error = RemoteDataSourceException("Failed to fetch data")
        whenever(getPlanetsUseCase()).thenReturn(flowOf(Result.failure(error)))

        // Set up a listener to observe the planetsState
        val job = launch {
            planetsViewModel.planetsState.collectLatest { state ->
                // Assert the state when it changes
                if (state is PlanetsState.Error) {
                    assertEquals("Failed to fetch data", state.errorMessage)
                }
            }
        }

        // Act
        planetsViewModel.planetEvent(PlanetEvent.FetchPlanets)

        // Wait for all coroutines to complete
        advanceUntilIdle()

        // Clean up
        job.cancel() // Cancel the collection job to stop observing state
    }


    @Test
    fun `loadMorePlanets should emit error state on failure to fetch more planets`() =
        runTest {
            // Arrange
            val error = RemoteDataSourceException("Failed to fetch more data")
            whenever(getNextPageUseCase(any())).thenReturn(flowOf(Result.failure(error)))

            // Set up a listener to observe the planetsState
            val job = launch {
                planetsViewModel.planetsState.collectLatest { state ->
                    // Assert the state when it changes
                    if (state is PlanetsState.Error) {
                        assertEquals("Failed to fetch more data", state.errorMessage)
                    }
                }
            }

            // Act
            planetsViewModel.planetEvent(PlanetEvent.LoadMorePlanets)

            // Wait for all coroutines to complete
            advanceUntilIdle()

            // Clean up
            job.cancel() // Cancel the collection job to stop observing state
        }


    @Test
    fun `loadMorePlanets should append new planets to current list on success`() = runTest {
        // Arrange
        val newPlanetResponse = PlanetPageData(
            next = null, // No next page
            results = listOf(
                Planet(
                    name = "Mars",
                    climate = "Arid",
                    orbitalPeriod = "687",
                    gravity = "3.7"
                )
            )
        )
        // Mock the use case to return a successful response for loading more planets
        whenever(getNextPageUseCase(any())).thenReturn(flowOf(Result.success(newPlanetResponse)))

        // Act
        planetsViewModel.planetEvent(PlanetEvent.FetchPlanets) // Initial planet load
        planetsViewModel.planetEvent(PlanetEvent.LoadMorePlanets) // Simulate loading more planets

        // Collect the latest state changes and assert after the flow emits the new state
        val job = launch {
            planetsViewModel.planetsState.collectLatest { state ->
                // Assert the state when it changes
                val expectedPlanets = planetResponse.results + newPlanetResponse.results
                assertEquals(PlanetsState.Success(expectedPlanets), state)
            }
        }

        // Wait for all coroutines to complete
        advanceUntilIdle()

        // Clean up
        job.cancel() // Cancel the collection job to stop observing state
    }

    @Test
    fun `getPlanets should emit error state on NoNetworkException`() = runTest {
        // Arrange
        val noNetworkException = NoNetworkException("No internet connection")
        whenever(getPlanetsUseCase()).thenReturn(flowOf(Result.failure(noNetworkException)))

        // Set up a listener to observe the planetsState
        val job = launch {
            planetsViewModel.planetsState.collectLatest { state ->
                // Assert the state when it changes
                if (state is PlanetsState.Error) {
                    assertEquals("No internet connection", state.errorMessage)
                }
            }
        }

        // Act: Trigger the event to fetch planets
        planetsViewModel.planetEvent(PlanetEvent.FetchPlanets)

        // Wait for all coroutines to complete
        advanceUntilIdle()

        // Clean up
        job.cancel() // Cancel the collection job to stop observing state
    }

    @Test
    fun `getPlanets should emit error state on Other Exceptions`() = runTest {
        // Arrange
        val otherException = Exception("Some unknown error")
        whenever(getPlanetsUseCase()).thenReturn(flowOf(Result.failure(otherException)))

        // Set up a listener to observe the planetsState
        val job = launch {
            planetsViewModel.planetsState.collectLatest { state ->
                // Assert the state when it changes
                if (state is PlanetsState.Error) {
                    assertEquals("Some unknown error", state.errorMessage)
                }
            }
        }

        // Act: Trigger the event to fetch planets
        planetsViewModel.planetEvent(PlanetEvent.FetchPlanets)

        // Wait for all coroutines to complete
        advanceUntilIdle()

        // Clean up
        job.cancel() // Cancel the collection job to stop observing state
    }

    @Test
    fun `loadMorePlanets should emit error state on Other Exceptions`() = runTest {
        // Arrange
        val otherException = Exception("Some unknown error")
        whenever(getNextPageUseCase(any())).thenReturn(flowOf(Result.failure(otherException)))

        // Set up a listener to observe the planetsState
        val job = launch {
            planetsViewModel.planetsState.collectLatest { state ->
                // Assert the state when it changes
                if (state is PlanetsState.Error) {
                    assertEquals("Some unknown error", state.errorMessage)
                }
            }
        }

        // Act: Trigger the event to fetch planets
        planetsViewModel.planetEvent(PlanetEvent.FetchPlanets)

        // Wait for all coroutines to complete
        advanceUntilIdle()

        // Clean up
        job.cancel() // Cancel the collection job to stop observing state
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to avoid affecting other tests
    }
}
