package lk.chamiviews.starwarsplanets.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import lk.chamiviews.starwarsplanets.data.model.CachedPlanet

/**
 * Room Database for managing planet-related data.
 *
 * This database contains a single entity: [CachedPlanet]. It provides access to
 * the [PlanetDao] for performing operations on planet data.
 */
@Database(entities = [CachedPlanet::class], version = 1)
abstract class PlanetDatabase : RoomDatabase() {

    /**
     * Provides access to the [PlanetDao] for performing database operations on planet data.
     *
     * @return The [PlanetDao] instance for interacting with the database.
     */
    abstract fun planetDao(): PlanetDao
}