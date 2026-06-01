package com.anenha.superhero.domain.usecase

import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.repository.SuperHeroRepository

class SearchHeroesUseCase(private val repository: SuperHeroRepository) {
    suspend operator fun invoke(query: String): Result<List<SuperHero>> {
        val trimmed = query.trim()
        if (trimmed.length < 3) {
            return Result.success(emptyList())
        }
        return repository.searchHeroes(trimmed)
    }
}
