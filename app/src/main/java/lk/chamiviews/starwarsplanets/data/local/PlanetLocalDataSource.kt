package lk.chamiviews.starwarsplanets.data.local

import lk.chamiviews.starwarsplanets.data.model.CachedPlanet

/**
 * Interface for a local data source that provides CRUD operations for managing Planet entities.
 */
interface PlanetLocalDataSource {
    suspend fun savePlanets(planets: List<CachedPlanet>)
    suspend fun getPlanets(): List<CachedPlanet>
    suspend fun getPlanetsCount(): Long
}
