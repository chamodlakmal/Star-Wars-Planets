package lk.chamiviews.starwarsplanets.data.model

data class PlanetResponse(
    val count: Long,
    val next: String?,
    val previous: Any?,
    val results: List<PlanetDto>,
)