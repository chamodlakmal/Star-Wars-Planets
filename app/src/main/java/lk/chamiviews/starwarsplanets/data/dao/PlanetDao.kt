package lk.chamiviews.starwarsplanets.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lk.chamiviews.starwarsplanets.data.entity.PlanetEntity

@Dao
interface PlanetDao {
    @Query("SELECT * FROM planets")
    fun getPlanets(): PagingSource<Int, PlanetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(planets: List<PlanetEntity>)
}