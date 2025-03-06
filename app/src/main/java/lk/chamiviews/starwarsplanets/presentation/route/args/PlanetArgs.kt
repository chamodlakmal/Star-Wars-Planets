package lk.chamiviews.starwarsplanets.presentation.route.args

import kotlinx.serialization.Serializable

@Serializable
object SplashScreenArgs

@Serializable
object PlanetListScreenArgs

@Serializable
data class PlanetDetailScreenArgs(
    val index: Int,
    val name: String,
    val gravity: String,
    val orbitalPeriod: String,
    val climate: String
)