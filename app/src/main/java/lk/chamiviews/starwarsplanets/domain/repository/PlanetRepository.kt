package lk.chamiviews.starwarsplanets.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import lk.chamiviews.starwarsplanets.domain.model.Planet

interface PlanetRepository {
    fun getPlanets(): Flow<PagingData<Planet>>
    suspend fun getPlanetById(id: Int): Planet?
}