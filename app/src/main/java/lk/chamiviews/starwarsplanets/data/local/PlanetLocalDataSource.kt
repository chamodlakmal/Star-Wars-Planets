package lk.chamiviews.starwarsplanets.data.local

import kotlinx.coroutines.flow.Flow
import lk.chamiviews.starwarsplanets.data.model.CachedPlanet

/**
 * Interface for the local data source that manages planet data in the local storage.
 */
interface PlanetLocalDataSource {
    /**
     * Saves a list of planets to the local storage.
     *
     * @param planets The list of planets to be saved.
     */
    fun savePlanets(planets: List<CachedPlanet>)

    /**
     * Retrieves the list of planets from the local storage.
     *
     * @return A [Flow] emitting the list of cached planets stored in the local database.
     */
    fun getPlanets(): Flow<List<CachedPlanet>>
}
