package lk.chamiviews.starwarsplanets.presentation.args

import kotlinx.serialization.Serializable

@Serializable
object PlanetListScreenArgs

@Serializable
data class PlanetDetailScreenArgs(
    val name: String,
    val climate: String
)