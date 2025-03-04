package lk.chamiviews.starwarsplanets.data.remote

import lk.chamiviews.starwarsplanets.data.model.PlanetResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface PlanetApi {

    @GET("planets")
    suspend fun getPlanets(): PlanetResponse

    @GET
    suspend fun getNextPage(@Url nextPageUrl: String): PlanetResponse
}