package lk.chamiviews.starwarsplanets.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lk.chamiviews.starwarsplanets.data.model.CachedPlanet
import javax.inject.Inject


class PlanetLocalDataSourceImpl @Inject constructor(
    private val planetDao: PlanetDao
) : PlanetLocalDataSource {

    override suspend fun savePlanets(planets: List<CachedPlanet>) {
        withContext(Dispatchers.IO) {
            planetDao.insertPlanets(planets)
        }
    }

    override suspend fun getPlanets(): List<CachedPlanet> {
        return withContext(Dispatchers.IO) {
            planetDao.getAllPlanets()
        }
    }

    override suspend fun getPlanetsCount(): Long {
        return withContext(Dispatchers.IO) {
            planetDao.getPlanetsCount()
        }
    }
}