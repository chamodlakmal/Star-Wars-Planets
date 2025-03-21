package lk.chamiviews.starwarsplanets.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lk.chamiviews.starwarsplanets.domain.model.PlanetPageData
import lk.chamiviews.starwarsplanets.domain.model.toPlanetPageData
import lk.chamiviews.starwarsplanets.domain.repository.PlanetRepository

class GetNextPageUseCase(private val repository: PlanetRepository) {
    operator fun invoke(nextPageUrl: String): Flow<Result<PlanetPageData>> =
        repository.getNextPage(nextPageUrl).map { result ->
            result.mapCatching { it.toPlanetPageData() }
        }
}
