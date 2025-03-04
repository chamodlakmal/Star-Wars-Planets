package lk.chamiviews.starwarsplanets.data.remote

import lk.chamiviews.starwarsplanets.data.model.PlanetResponse
import lk.chamiviews.starwarsplanets.data.remote.PlanetApi
import lk.chamiviews.starwarsplanets.data.remote.PlanetRemoteDataSource
import lk.chamiviews.starwarsplanets.utils.NoNetworkException
import lk.chamiviews.starwarsplanets.utils.RemoteDataSourceException
import java.io.IOException
import javax.inject.Inject

class PlanetRemoteDataSourceImpl @Inject constructor(
    private val planetApi: PlanetApi
) : PlanetRemoteDataSource {
    override suspend fun getPlanets(): PlanetResponse {
        try {
            return planetApi.getPlanets()
        } catch (e: Exception) {
            throw getRemoteException(e)
        }
    }

    override suspend fun getNextPage(nextPageUrl: String): PlanetResponse {
        try {
            return planetApi.getNextPage(nextPageUrl)
        } catch (e: Exception) {
            throw getRemoteException(e)
        }
    }

    private fun getRemoteException(e: Exception): Exception {
        return when (e) {
            is IOException -> NoNetworkException()
            else -> RemoteDataSourceException(e.message ?: "Unknown error")
        }
    }
}