package lk.chamiviews.starwarsplanets.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lk.chamiviews.starwarsplanets.data.model.CachedPlanet

@Dao
interface PlanetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlanets(planets: List<CachedPlanet>)

    @Query("SELECT * FROM cached_planet")
    fun getAllPlanets(): Flow<List<CachedPlanet>>
}