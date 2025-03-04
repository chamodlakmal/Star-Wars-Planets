package lk.chamiviews.starwarsplanets.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import lk.chamiviews.starwarsplanets.data.model.Planet

@Database(entities = [Planet::class], version = 1)
abstract class PlanetDatabase : RoomDatabase() {

    abstract fun planetDao(): PlanetDao
}