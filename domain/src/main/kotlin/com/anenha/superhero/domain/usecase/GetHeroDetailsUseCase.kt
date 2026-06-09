package com.anenha.superhero.domain.usecase

import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.repository.SuperHeroRepository

/**
 * Use case to retrieve the complete details of a specific [SuperHero].
 *
 * @property repository The [SuperHeroRepository] to fetch data from.
 */
class GetHeroDetailsUseCase(private val repository: SuperHeroRepository) {
    /**
     * Executes the fetch for hero details.
     *
     * @param id The unique identifier of the hero.
     * @return A [Result] containing the [SuperHero] or an exception.
     */
    suspend operator fun invoke(id: String): Result<SuperHero> {
        return repository.getHero(id)
    }
}
