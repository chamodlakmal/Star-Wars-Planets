package lk.chamiviews.starwarsplanets.data.mapper

import lk.chamiviews.starwarsplanets.data.model.Planet
import lk.chamiviews.starwarsplanets.data.model.PlanetDto

fun PlanetDto.toPlanet(): Planet {
    return Planet(
        name = name,
        climate = climate,
    )
}

fun Planet.toPlanetDto(): PlanetDto {
    return PlanetDto(
        name = name,
        climate = climate
    )
}