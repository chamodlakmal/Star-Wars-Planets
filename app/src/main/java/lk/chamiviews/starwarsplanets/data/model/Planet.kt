package lk.chamiviews.starwarsplanets.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planet")
data class Planet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val climate: String
)
