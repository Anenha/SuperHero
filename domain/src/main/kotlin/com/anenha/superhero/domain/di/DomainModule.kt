package com.anenha.superhero.domain.di

import com.anenha.superhero.domain.repository.SuperHeroRepository
import com.anenha.superhero.domain.usecase.GetHeroDetailsUseCase
import com.anenha.superhero.domain.usecase.GetRandomInitialHeroesUseCase
import com.anenha.superhero.domain.usecase.SearchHeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetHeroDetailsUseCase(
        repository: SuperHeroRepository
    ): GetHeroDetailsUseCase {
        return GetHeroDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetRandomInitialHeroesUseCase(
        repository: SuperHeroRepository
    ): GetRandomInitialHeroesUseCase {
        return GetRandomInitialHeroesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchHeroesUseCase(
        repository: SuperHeroRepository
    ): SearchHeroesUseCase {
        return SearchHeroesUseCase(repository)
    }
}
