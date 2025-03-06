package lk.chamiviews.starwarsplanets.data.remote


import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import lk.chamiviews.starwarsplanets.data.model.PlanetDto
import lk.chamiviews.starwarsplanets.data.model.PlanetResponse
import lk.chamiviews.starwarsplanets.utils.NoNetworkException
import lk.chamiviews.starwarsplanets.utils.RemoteDataSourceException
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import kotlin.test.assertFailsWith


@RunWith(MockitoJUnitRunner::class)
class PlanetRemoteDataSourceImplTest {

    @MockK
    private lateinit var planetApi: PlanetApi

    private lateinit var planetRemoteDataSource: PlanetRemoteDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        planetRemoteDataSource = PlanetRemoteDataSourceImpl(planetApi)
    }

    @Test
    fun `getPlanets should return PlanetResponse on success`() = runTest {
        // Prepare the mock response
        val planetDto = PlanetDto(
            name = "Earth",
            climate = "Temperate",
            orbitalPeriod = "365 days",
            gravity = "1.0",
            url = "https://swapi.dev/api/planets/1/"
        )
        val planetResponse = PlanetResponse(
            next = null,
            results = listOf(planetDto)
        )

        // Mock the API call to return the planetResponse
        coEvery { planetApi.getPlanets() } returns planetResponse

        // Call the method under test and collect the emitted value
        val result = planetRemoteDataSource.getPlanets().first()

        // Verify the result
        assertEquals(planetResponse.results[0].name, result.results[0].name)
        assertEquals(planetResponse.results, result.results)

        // Verify that the mock API method was called
        coVerify { planetApi.getPlanets() }
    }

    @Test
    fun `getNextPage should return PlanetResponse on success`() = runTest {
        val planetDto = PlanetDto(
            name = "Mars",
            climate = "Arid",
            orbitalPeriod = "687 days",
            gravity = "0.38",
            url = "https://swapi.dev/api/planets/4/"
        )
        val planetResponse = PlanetResponse(
            next = "nextPageUrl",
            results = listOf(planetDto)
        )
        val nextPageUrl = "nextPageUrl"
        coEvery { planetApi.getNextPage(nextPageUrl) } returns planetResponse

        val result = planetRemoteDataSource.getNextPage(nextPageUrl).first()

        assertEquals(planetResponse, result)
        coVerify { planetApi.getNextPage(nextPageUrl) }
    }

    @Test
    fun `getPlanets should throw NoNetworkException on IOException`() = runTest {
        val exception = IOException("No network connection available")
        coEvery { planetApi.getPlanets() } throws exception

        val thrownException = assertFailsWith<NoNetworkException> {
            planetRemoteDataSource.getPlanets()
                .first()
        }

        assertEquals(exception.message, thrownException.message)
        coVerify { planetApi.getPlanets() }
    }

    @Test
    fun `getNextPage should throw NoNetworkException on IOException`() = runTest {
        val exception = IOException("No network connection available")
        val nextPageUrl = "nextPageUrl"
        coEvery { planetApi.getNextPage(nextPageUrl) } throws exception

        val thrownException = assertFailsWith<NoNetworkException> {
            planetRemoteDataSource.getNextPage(nextPageUrl)
                .first()
        }

        assertEquals(exception.message, thrownException.message)
        coVerify { planetApi.getNextPage(nextPageUrl) }
    }

    @Test
    fun `getPlanets should throw RemoteDataSourceException for other exceptions`() = runTest {
        val exception = Exception("Some other error")
        coEvery { planetApi.getPlanets() } throws exception

        val thrownException = assertFailsWith<RemoteDataSourceException> {
            planetRemoteDataSource.getPlanets()
                .first()
        }

        assertEquals(exception.message, thrownException.message)
        coVerify { planetApi.getPlanets() }
    }

    @Test
    fun `getNextPage should throw RemoteDataSourceException for other exceptions`() = runTest {
        val exception = Exception("Some other error")
        val nextPageUrl = "nextPageUrl"
        coEvery { planetApi.getNextPage(nextPageUrl) } throws exception

        val thrownException = assertFailsWith<RemoteDataSourceException> {
            planetRemoteDataSource.getNextPage(nextPageUrl)
                .first()
        }

        assertEquals(exception.message, thrownException.message)
        coVerify { planetApi.getNextPage(nextPageUrl) }
    }


}
