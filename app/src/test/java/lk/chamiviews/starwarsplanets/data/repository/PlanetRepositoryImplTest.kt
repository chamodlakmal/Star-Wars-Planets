package lk.chamiviews.starwarsplanets.data.repository

import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import lk.chamiviews.starwarsplanets.data.local.PlanetLocalDataSource
import lk.chamiviews.starwarsplanets.data.mapper.toPlanetDto
import lk.chamiviews.starwarsplanets.data.model.CachedPlanet
import lk.chamiviews.starwarsplanets.data.model.PlanetDto
import lk.chamiviews.starwarsplanets.data.model.PlanetResponse
import lk.chamiviews.starwarsplanets.data.remote.PlanetRemoteDataSource
import lk.chamiviews.starwarsplanets.utils.NoNetworkException
import lk.chamiviews.starwarsplanets.utils.RemoteDataSourceException
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.Test
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class PlanetRepositoryImplTest {

    @MockK
    private lateinit var remoteDataSource: PlanetRemoteDataSource

    @MockK
    private lateinit var localDataSource: PlanetLocalDataSource

    private lateinit var planetRepository: PlanetRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        planetRepository = PlanetRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getPlanets should fetch from remote and save to local on success`() = runTest {
        // Given a mock response from the remote data source
        val planetDto = PlanetDto(
            name = "Earth",
            climate = "Temperate",
            orbitalPeriod = "365 days",
            gravity = "1.0",
            url = "https://swapi.dev/api/planets/1/"
        )
        val planetResponse = PlanetResponse(
            next = "https://swapi.dev/api/planets/?page=2",
            results = listOf(planetDto)
        )

        coEvery { remoteDataSource.getPlanets() } returns flowOf(planetResponse)
        every { localDataSource.savePlanets(any()) } just Runs

        // When calling getPlanets
        val result = planetRepository.getPlanets().first()

        // Then it should return the expected PlanetResponse
        assertTrue(result.isSuccess)
        assertEquals(planetResponse.results[0].name, result.getOrThrow().results[0].name)
        assertEquals(planetResponse.next, result.getOrThrow().next)

        // Verify that the remote data was saved locally
        verify { localDataSource.savePlanets(any()) }
    }

    @Test
    fun `getPlanets should fetch from local storage when there is no network`() = runTest {
        // Given no network exception
        val noNetworkException = NoNetworkException("No internet connection")

        // Mock local database response
        val cachedPlanets = listOf(
            CachedPlanet(
                id = 1,
                name = "Earth",
                climate = "Temperate",
                orbitalPeriod = "365 days",
                gravity = "1.0"
            )
        )
        val expectedResponse =
            PlanetResponse(next = null, results = cachedPlanets.map { it.toPlanetDto() })

        coEvery { remoteDataSource.getPlanets() } throws noNetworkException
        coEvery { localDataSource.getPlanets() } returns flowOf(cachedPlanets)

        // When calling getPlanets
        val result = planetRepository.getPlanets().first()

        // Then it should return the cached data
        assertTrue(result.isSuccess)
        assertEquals(expectedResponse.results[0].name, result.getOrThrow().results[0].name)

        // Verify that local storage was queried
        verify { localDataSource.getPlanets() }
    }

    @Test
    fun `getPlanets should return failure when there is no network and local data is empty`() =
        runTest {
            // Given no network exception and empty local storage
            val noNetworkException = NoNetworkException("No internet connection")

            coEvery { remoteDataSource.getPlanets() } throws noNetworkException
            coEvery { localDataSource.getPlanets() } returns flowOf(emptyList())

            // When calling getPlanets
            val result = planetRepository.getPlanets().first()

            // Then it should return failure
            assertTrue(result.isFailure)
            assertEquals(noNetworkException, result.exceptionOrNull())

            // Verify that local storage was queried
            verify { localDataSource.getPlanets() }
        }

    @Test
    fun `getNextPage should fetch from remote and save to local on success`() = runTest {
        // Given a next page URL and a response from remote data source
        val nextPageUrl = "https://swapi.dev/api/planets/?page=2"
        val planetDto = PlanetDto(
            name = "Mars",
            climate = "Arid",
            orbitalPeriod = "687 days",
            gravity = "0.38",
            url = "https://swapi.dev/api/planets/2/"
        )
        val planetResponse = PlanetResponse(next = null, results = listOf(planetDto))

        coEvery { remoteDataSource.getNextPage(nextPageUrl) } returns flowOf(planetResponse)
        every { localDataSource.savePlanets(any()) } just Runs

        // When calling getNextPage
        val result = planetRepository.getNextPage(nextPageUrl).first()

        // Then it should return the expected PlanetResponse
        assertTrue(result.isSuccess)
        assertEquals(planetResponse.results[0].name, result.getOrThrow().results[0].name)

        // Verify that the remote data was saved locally
        verify { localDataSource.savePlanets(any()) }
    }

    @Test
    fun `getNextPage should return failure when there is no network`() = runTest {
        // Given a next page URL and a network exception
        val nextPageUrl = "https://swapi.dev/api/planets/?page=2"
        val noNetworkException = NoNetworkException("No internet connection")

        coEvery { remoteDataSource.getNextPage(nextPageUrl) } throws noNetworkException

        // When calling getNextPage
        val result = planetRepository.getNextPage(nextPageUrl).first()

        // Then it should return failure
        assertTrue(result.isFailure)
        assertEquals(noNetworkException, result.exceptionOrNull())
    }

    @Test
    fun `getPlanets should return failure when remote data source throws RemoteDataSourceException`() =
        runTest {
            // Given a RemoteDataSourceException
            val remoteException = RemoteDataSourceException("Server error")

            coEvery { remoteDataSource.getPlanets() } throws remoteException

            // When calling getPlanets
            val result = planetRepository.getPlanets().first()

            // Then it should return failure
            assertTrue(result.isFailure)
            assertEquals(remoteException, result.exceptionOrNull())
        }

    @Test
    fun `getNextPage should return failure when remote data source throws RemoteDataSourceException`() =
        runTest {
            // Given a RemoteDataSourceException
            val nextPageUrl = "https://swapi.dev/api/planets/?page=2"
            val remoteException = RemoteDataSourceException("Server error")

            coEvery { remoteDataSource.getNextPage(nextPageUrl) } throws remoteException

            // When calling getNextPage
            val result = planetRepository.getNextPage(nextPageUrl).first()

            // Then it should return failure
            assertTrue(result.isFailure)
            assertEquals(remoteException, result.exceptionOrNull())
        }

}
