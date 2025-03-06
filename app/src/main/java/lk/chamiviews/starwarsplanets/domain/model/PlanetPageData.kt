package lk.chamiviews.starwarsplanets.domain.model

import lk.chamiviews.starwarsplanets.data.model.PlanetResponse

data class PlanetPageData(
    val next: String?,
    val results: List<Planet>
)

fun PlanetResponse.toPlanetPageData(): PlanetPageData {
    return PlanetPageData(
        next = next,
        results = results.map { it.toPlanet() }
    )
}
