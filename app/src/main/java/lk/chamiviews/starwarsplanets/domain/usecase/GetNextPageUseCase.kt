package lk.chamiviews.starwarsplanets.domain.usecase

import lk.chamiviews.starwarsplanets.domain.repository.PlanetRepository

class GetNextPageUseCase(private val repository: PlanetRepository) {
    suspend operator fun invoke(nextPageUrl: String) = repository.getNextPage(nextPageUrl)
}