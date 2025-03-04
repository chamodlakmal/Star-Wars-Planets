package lk.chamiviews.starwarsplanets.data.local

import lk.chamiviews.starwarsplanets.data.model.Planet

/**
 * Interface for a local data source that provides CRUD operations for managing Planet entities.
 */
interface PlanetLocalDataSource {
    suspend fun savePlanets(planets: List<Planet>)
    suspend fun getPlanets(): List<Planet>
    suspend fun getPlanetsCount(): Long
}
