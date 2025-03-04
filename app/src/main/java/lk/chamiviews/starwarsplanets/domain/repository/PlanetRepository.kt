package lk.chamiviews.starwarsplanets.domain.repository

import lk.chamiviews.starwarsplanets.data.model.PlanetResponse

interface PlanetRepository {
    suspend fun getPlanets(): Result<PlanetResponse>
    suspend fun getNextPage(nextPageUrl: String): Result<PlanetResponse>
}