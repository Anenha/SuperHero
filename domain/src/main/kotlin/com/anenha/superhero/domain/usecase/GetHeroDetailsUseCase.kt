package com.anenha.superhero.domain.usecase

import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.repository.SuperHeroRepository

class GetHeroDetailsUseCase(private val repository: SuperHeroRepository) {
    suspend operator fun invoke(id: String): Result<SuperHero> {
        return repository.getHero(id)
    }
}
