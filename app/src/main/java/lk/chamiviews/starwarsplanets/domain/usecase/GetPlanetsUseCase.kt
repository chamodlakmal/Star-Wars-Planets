package lk.chamiviews.starwarsplanets.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lk.chamiviews.starwarsplanets.domain.model.PlanetPageData
import lk.chamiviews.starwarsplanets.domain.model.toPlanetPageData
import lk.chamiviews.starwarsplanets.domain.repository.PlanetRepository

class GetPlanetsUseCase(private val repository: PlanetRepository) {
    operator fun invoke(): Flow<Result<PlanetPageData>> = flow {
        repository.getPlanets().collect { result ->
            result.onSuccess {
                emit(Result.success(it.toPlanetPageData()))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }

}