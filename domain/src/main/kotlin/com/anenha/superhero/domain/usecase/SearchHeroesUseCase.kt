package com.anenha.superhero.domain.usecase

import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.repository.SuperHeroRepository

/**
 * Use case to search for superheroes based on a name query.
 *
 * @property repository The [SuperHeroRepository] used to perform the search.
 */
class SearchHeroesUseCase(private val repository: SuperHeroRepository) {
    /**
     * Performs a search if the query is at least 3 characters long.
     *
     * @param query The search string entered by the user.
     * @return A [Result] containing a list of matching [SuperHero]es.
     * Returns an empty list if the query is too short.
     */
    suspend operator fun invoke(query: String): Result<List<SuperHero>> {
        val trimmed = query.trim()
        if (trimmed.length < 3) {
            return Result.success(emptyList())
        }
        return repository.searchHeroes(trimmed)
    }
}
