package lk.chamiviews.starwarsplanets.data.mapper

import lk.chamiviews.starwarsplanets.data.model.CachedPlanet
import lk.chamiviews.starwarsplanets.data.model.PlanetDto

fun PlanetDto.toCachedPlanet(id: Int = 0): CachedPlanet {
    return CachedPlanet(
        id = id,
        name = name,
        climate = climate,
        orbitalPeriod = orbitalPeriod,
        gravity = gravity
    )
}

fun CachedPlanet.toPlanetDto(): PlanetDto {
    return PlanetDto(
        name = name,
        climate = climate,
        orbitalPeriod = orbitalPeriod,
        gravity = gravity
    )
}