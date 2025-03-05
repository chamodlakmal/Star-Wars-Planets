package lk.chamiviews.starwarsplanets.data.remote

import kotlinx.coroutines.flow.Flow
import lk.chamiviews.starwarsplanets.data.model.PlanetResponse

interface PlanetRemoteDataSource {

    fun getPlanets(): Flow<PlanetResponse>

    fun getNextPage(nextPageUrl: String): Flow<PlanetResponse>
}