package lk.chamiviews.starwarsplanets.data.repository

import lk.chamiviews.starwarsplanets.data.local.PlanetLocalDataSource
import lk.chamiviews.starwarsplanets.data.mapper.toPlanet
import lk.chamiviews.starwarsplanets.data.mapper.toPlanetDto
import lk.chamiviews.starwarsplanets.data.model.PlanetResponse
import lk.chamiviews.starwarsplanets.data.remote.PlanetRemoteDataSource
import lk.chamiviews.starwarsplanets.domain.repository.PlanetRepository
import lk.chamiviews.starwarsplanets.utils.NoNetworkException
import lk.chamiviews.starwarsplanets.utils.RemoteDataSourceException
import javax.inject.Inject

class PlanetRepositoryImpl @Inject constructor(
    private val remoteDataSource: PlanetRemoteDataSource,
    private val localDataSource: PlanetLocalDataSource
) : PlanetRepository {
    private var currentPageUrl: String? = null

    override suspend fun getPlanets(): Result<PlanetResponse> {
        return try {
            val response = remoteDataSource.getPlanets()
            currentPageUrl = response.next
            localDataSource.savePlanets(response.results.map { it.toPlanet() })
            Result.success(response)
        } catch (e: NoNetworkException) {
            val cachedPlanets = localDataSource.getPlanets().map { it.toPlanetDto() }
            when {
                cachedPlanets.isEmpty() -> Result.failure(e)
                else -> {
                    val cachedResponse = PlanetResponse(
                        count = localDataSource.getPlanetsCount(),
                        next = currentPageUrl,
                        previous = null,
                        results = cachedPlanets
                    )
                    Result.success(cachedResponse)
                }
            }
        } catch (e: RemoteDataSourceException) {
            Result.failure(e)
        }
    }

    override suspend fun getNextPage(nextPageUrl: String): Result<PlanetResponse> {
        return try {
            val response = remoteDataSource.getNextPage(nextPageUrl)
            currentPageUrl = response.next
            localDataSource.savePlanets(response.results.map { it.toPlanet() })
            Result.success(response)

        } catch (e: NoNetworkException) {
            Result.failure(e)
        } catch (e: RemoteDataSourceException) {
            Result.failure(e)
        }
    }

}