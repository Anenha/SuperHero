package com.anenha.superhero.core.network.di

import android.content.Context
import com.anenha.superhero.core.network.BuildConfig
import com.anenha.superhero.core.network.interceptor.CacheInterceptor
import com.anenha.superhero.core.network.interceptor.SanitizeJsonInterceptor
import com.anenha.superhero.core.network.repository.SuperHeroRepositoryImpl
import com.anenha.superhero.core.network.service.SuperHeroService
import com.anenha.superhero.domain.repository.SuperHeroRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
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
        fun provideOkHttpClient(
            @ApplicationContext context: Context
        ): OkHttpClient {
            val cacheSize = 50 * 1024 * 1024L // 50 MiB
            val cache = Cache(context.cacheDir, cacheSize)

            return OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(SanitizeJsonInterceptor())
                .addNetworkInterceptor(CacheInterceptor())
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
