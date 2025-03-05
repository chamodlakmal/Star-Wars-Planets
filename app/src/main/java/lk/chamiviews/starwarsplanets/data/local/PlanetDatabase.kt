package lk.chamiviews.starwarsplanets.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import lk.chamiviews.starwarsplanets.data.model.CachedPlanet

@Database(entities = [CachedPlanet::class], version = 1)
abstract class PlanetDatabase : RoomDatabase() {

    abstract fun planetDao(): PlanetDao
}