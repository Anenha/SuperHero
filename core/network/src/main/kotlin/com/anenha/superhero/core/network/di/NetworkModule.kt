package com.anenha.superhero.core.network.di

import com.anenha.superhero.core.network.BuildConfig
import com.anenha.superhero.core.network.interceptor.SanitizeJsonInterceptor
import com.anenha.superhero.core.network.SuperHeroRepositoryImpl
import com.anenha.superhero.core.network.SuperHeroService
import com.anenha.superhero.domain.repository.SuperHeroRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindSuperHeroRepository(
        superHeroRepositoryImpl: SuperHeroRepositoryImpl
    ): SuperHeroRepository

    companion object {
        @Provides
        @Singleton
        fun provideJson(): Json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(SanitizeJsonInterceptor())
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(
            json: Json,
            okHttpClient: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://superheroapi.com/api/${BuildConfig.SUPERHERO_ACCESS_TOKEN}/")
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
        }

        @Provides
        @Singleton
        fun provideSuperHeroService(retrofit: Retrofit): SuperHeroService {
            return retrofit.create(SuperHeroService::class.java)
        }
    }
}
