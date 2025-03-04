package lk.chamiviews.starwarsplanets.presentation.state

import lk.chamiviews.starwarsplanets.data.model.Planet

sealed class PlanetsState {
    data object Loading : PlanetsState()
    data class Success(val planets: List<Planet>) : PlanetsState()
    data class Error(val errorMessage: String) : PlanetsState()
}