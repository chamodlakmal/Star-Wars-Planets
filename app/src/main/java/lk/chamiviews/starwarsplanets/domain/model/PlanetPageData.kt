package lk.chamiviews.starwarsplanets.domain.model

import lk.chamiviews.starwarsplanets.data.model.PlanetResponse

data class PlanetPageData(
    val count: Long,
    val next: String?,
    val previous: Any?,
    val results: List<Planet>
)

fun PlanetResponse.toPlanetPageData(): PlanetPageData {
    return PlanetPageData(
        count = count,
        next = next,
        previous = previous,
        results = results.map { it.toPlanet() }
    )
}


