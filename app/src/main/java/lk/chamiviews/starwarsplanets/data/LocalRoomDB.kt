package lk.chamiviews.starwarsplanets.data

import androidx.room.Database
import androidx.room.RoomDatabase
import lk.chamiviews.starwarsplanets.data.entity.PlanetEntity

@Database(
    entities = [PlanetEntity::class], version = 1
)
abstract class LocalRoomDB : RoomDatabase() {

}