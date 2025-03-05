package lk.chamiviews.starwarsplanets.data.local

import kotlinx.coroutines.flow.Flow
import lk.chamiviews.starwarsplanets.data.model.CachedPlanet

/**
 * Interface for a local data source that provides CRUD operations for managing Planet entities.
 */
interface PlanetLocalDataSource {
    fun savePlanets(planets: List<CachedPlanet>)
    fun getPlanets(): Flow<List<CachedPlanet>>
}
