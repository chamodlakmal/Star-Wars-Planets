package lk.chamiviews.starwarsplanets.domain.usecase

import lk.chamiviews.starwarsplanets.domain.repository.PlanetRepository

class GetPlanetsUseCase(private val repository: PlanetRepository) {
    suspend operator fun invoke() = repository.getPlanets()
}