package lk.chamiviews.starwarsplanets.presentation.event

sealed class PlanetEvent {
    data object LoadMorePlanets : PlanetEvent()
    data object FetchPlanets : PlanetEvent()
}