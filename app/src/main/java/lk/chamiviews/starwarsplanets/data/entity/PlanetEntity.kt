package lk.chamiviews.starwarsplanets.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planets")
data class PlanetEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val climate: String,
    val terrain: String
)