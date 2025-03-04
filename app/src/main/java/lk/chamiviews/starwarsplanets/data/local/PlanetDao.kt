package lk.chamiviews.starwarsplanets.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lk.chamiviews.starwarsplanets.data.model.Planet

@Dao
interface PlanetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanets(planets: List<Planet>)

    @Query("SELECT * FROM planet")
    fun getAllPlanets(): List<Planet>


    @Query("SELECT COUNT(*) FROM planet")
    fun getPlanetsCount(): Long
}