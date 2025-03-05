package lk.chamiviews.starwarsplanets.domain.model

import lk.chamiviews.starwarsplanets.data.model.PlanetDto

data class Planet(
    val name: String,
    val climate: String,
    val orbitalPeriod: String,
    val gravity: String
)


fun PlanetDto.toPlanet(): Planet {
    return Planet(
        name = name,
        climate = climate,
        orbitalPeriod = orbitalPeriod,
        gravity = gravity
    )
}
