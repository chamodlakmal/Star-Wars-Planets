package lk.chamiviews.starwarsplanets.data.mapper

import lk.chamiviews.starwarsplanets.data.model.Planet
import lk.chamiviews.starwarsplanets.data.model.PlanetDto

fun PlanetDto.toPlanet(id: Int = 0): Planet {
    return Planet(
        id = id,
        name = name,
        climate = climate,
        orbitalPeriod = orbitalPeriod,
        gravity = gravity
    )
}

fun Planet.toPlanetDto(): PlanetDto {
    return PlanetDto(
        name = name,
        climate = climate,
        orbitalPeriod = orbitalPeriod,
        gravity = gravity
    )
}