package lk.chamiviews.starwarsplanets.data.local

import kotlinx.coroutines.flow.Flow
import lk.chamiviews.starwarsplanets.data.model.CachedPlanet
import javax.inject.Inject


class PlanetLocalDataSourceImpl @Inject constructor(
    private val planetDao: PlanetDao
) : PlanetLocalDataSource {

    override fun savePlanets(planets: List<CachedPlanet>) {
        planetDao.insertPlanets(planets)
    }

    override fun getPlanets(): Flow<List<CachedPlanet>> {
        return planetDao.getAllPlanets()
    }
}