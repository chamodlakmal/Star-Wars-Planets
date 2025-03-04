package lk.chamiviews.starwarsplanets.data.remote

import lk.chamiviews.starwarsplanets.data.model.PlanetResponse

interface PlanetRemoteDataSource {

    suspend fun getPlanets(): PlanetResponse

    suspend fun getNextPage(nextPageUrl: String): PlanetResponse
}