package lk.chamiviews.starwarsplanets.data.model

data class PlanetResponse(
    val next: String?,
    val results: List<PlanetDto>
)