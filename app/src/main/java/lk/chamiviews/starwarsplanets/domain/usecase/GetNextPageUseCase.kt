package lk.chamiviews.starwarsplanets.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lk.chamiviews.starwarsplanets.domain.model.PlanetPageData
import lk.chamiviews.starwarsplanets.domain.model.toPlanetPageData
import lk.chamiviews.starwarsplanets.domain.repository.PlanetRepository

class GetNextPageUseCase(private val repository: PlanetRepository) {
    operator fun invoke(nextPageUrl: String): Flow<Result<PlanetPageData>> = flow {
        repository.getNextPage(nextPageUrl)
            .collect { result ->
                result.onSuccess {
                    emit(Result.success(it.toPlanetPageData()))
                }.onFailure {
                    emit(Result.failure(it))
                }
            }
    }
}
