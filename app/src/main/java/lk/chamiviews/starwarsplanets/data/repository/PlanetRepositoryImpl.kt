package lk.chamiviews.starwarsplanets.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import lk.chamiviews.starwarsplanets.data.local.PlanetLocalDataSource
import lk.chamiviews.starwarsplanets.data.mapper.toCachedPlanet
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

    override fun getPlanets(): Flow<Result<PlanetResponse>> = flow<Result<PlanetResponse>> {
        try {
            remoteDataSource.getPlanets().collect { response ->
                currentPageUrl = response.next
                localDataSource.savePlanets(response.results.map {
                    it.toCachedPlanet(
                        extractPlanetId(it.url)
                    )
                })
                emit(Result.success(response))
            }
        } catch (e: NoNetworkException) {
            localDataSource.getPlanets().collect { result ->
                val cachedPlanets = result.map { it.toPlanetDto() }
                when {
                    cachedPlanets.isEmpty() -> emit(Result.failure(e))
                    else -> {
                        val cachedResponse = PlanetResponse(
                            next = currentPageUrl, results = cachedPlanets
                        )
                        emit(Result.success(cachedResponse))
                    }
                }
            }

        } catch (e: RemoteDataSourceException) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getNextPage(nextPageUrl: String): Flow<Result<PlanetResponse>> = flow {
        try {
            remoteDataSource.getNextPage(nextPageUrl).collect { response ->
                currentPageUrl = response.next
                localDataSource.savePlanets(response.results.map {
                    it.toCachedPlanet(
                        extractPlanetId(
                            it.url
                        )
                    )
                })
                emit(Result.success(response))
            }


        } catch (e: NoNetworkException) {
            emit(Result.failure(e))
        } catch (e: RemoteDataSourceException) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun extractPlanetId(url: String): Int {
        return url.trimEnd('/').split("/").last().toInt()
    }

}