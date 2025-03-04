package lk.chamiviews.starwarsplanets.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lk.chamiviews.starwarsplanets.data.PlanetService
import lk.chamiviews.starwarsplanets.domain.model.Planet
import lk.chamiviews.starwarsplanets.domain.repository.PlanetRepository

class PlanetRepositoryImpl(private val planetService: PlanetService,
                           private val dao: PlanetDao,
                           private val pager: Pager<Int, PlanetEntity>) : PlanetRepository {
    override fun getPlanets(): Flow<PagingData<Planet>> {
        return pager.flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun getPlanetById(id: Int): Planet? {
        return dao.getPlanets().load(PagingSource.LoadParams.Refresh(0, 1, false)).data?.find { it.id == id }?.toDomain()
    }
}