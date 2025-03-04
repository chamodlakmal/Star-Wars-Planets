package lk.chamiviews.starwarsplanets.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lk.chamiviews.starwarsplanets.data.local.PlanetDatabase
import lk.chamiviews.starwarsplanets.data.local.PlanetLocalDataSource
import lk.chamiviews.starwarsplanets.data.local.PlanetLocalDataSourceImpl
import lk.chamiviews.starwarsplanets.data.remote.PlanetApi
import lk.chamiviews.starwarsplanets.data.remote.PlanetRemoteDataSource
import lk.chamiviews.starwarsplanets.data.remote.PlanetRemoteDataSourceImpl
import lk.chamiviews.starwarsplanets.data.repository.PlanetRepositoryImpl
import lk.chamiviews.starwarsplanets.domain.repository.PlanetRepository
import lk.chamiviews.starwarsplanets.domain.usecase.GetNextPageUseCase
import lk.chamiviews.starwarsplanets.domain.usecase.GetPlanetsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): PlanetApi = Retrofit.Builder()
        .baseUrl("https://swapi.dev/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PlanetApi::class.java)

    @Provides
    @Singleton
    fun providePlanetLocalDataSource(database: PlanetDatabase): PlanetLocalDataSource {
        return PlanetLocalDataSourceImpl(database.planetDao())
    }

    @Provides
    @Singleton
    fun providePlanetDatabase(@ApplicationContext context: Context): PlanetDatabase {
        return Room.databaseBuilder(
            context,
            PlanetDatabase::class.java, "planets_database"
        ).build()
    }


    @Provides
    @Singleton
    fun providePlanetRemoteDataSource(planetRemoteDataSourceImpl: PlanetRemoteDataSourceImpl): PlanetRemoteDataSource =
        planetRemoteDataSourceImpl

    @Provides
    @Singleton
    fun providePlanetRepository(
        planetRepositoryImpl: PlanetRepositoryImpl
    ): PlanetRepository = planetRepositoryImpl

    @Provides
    @Singleton
    fun provideGetPlanetsUseCase(repository: PlanetRepository) = GetPlanetsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetNextPageUseCase(repository: PlanetRepository) = GetNextPageUseCase(repository)

}