package lk.chamiviews.starwarsplanets.domain.repository

import kotlinx.coroutines.flow.Flow
import lk.chamiviews.starwarsplanets.data.model.PlanetResponse

interface PlanetRepository {
    fun getPlanets(): Flow<Result<PlanetResponse>>
    fun getNextPage(nextPageUrl: String): Flow<Result<PlanetResponse>>
}