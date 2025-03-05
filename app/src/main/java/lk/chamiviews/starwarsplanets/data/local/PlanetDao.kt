package lk.chamiviews.starwarsplanets.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lk.chamiviews.starwarsplanets.data.model.CachedPlanet

@Dao
interface PlanetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanets(planets: List<CachedPlanet>)

    @Query("SELECT * FROM cached_planet")
    fun getAllPlanets(): List<CachedPlanet>


    @Query("SELECT COUNT(*) FROM cached_planet")
    fun getPlanetsCount(): Long
}