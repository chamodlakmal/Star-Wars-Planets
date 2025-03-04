package lk.chamiviews.starwarsplanets.data

import retrofit2.http.GET
import retrofit2.http.Query


interface PlanetService {
    @GET("planets")
    suspend fun getPlanets(@Query("page") page: Int): PlanetResponse
}