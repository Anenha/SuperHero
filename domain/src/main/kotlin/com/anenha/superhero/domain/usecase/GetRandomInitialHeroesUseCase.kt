package com.anenha.superhero.domain.usecase

import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.repository.SuperHeroRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Use case to fetch a randomized selection of superheroes for the initial archive view.
 *
 * @property repository The [SuperHeroRepository] to fetch heroes from.
 */
class GetRandomInitialHeroesUseCase(private val repository: SuperHeroRepository) {
    /**
     * Fetches 10 random heroes by ID in parallel.
     *
     * @return A [Result] containing a list of 10 [SuperHero]es.
     */
    suspend operator fun invoke(): Result<List<SuperHero>> = coroutineScope {
        val randomIds = mutableSetOf<Int>()
        while (randomIds.size < 10) {
            randomIds.add((1..731).random())
        }

        val deferreds = randomIds.map { id ->
            async { repository.getHero(id.toString()) }
        }

        val results = deferreds.awaitAll()
        val heroes = results.mapNotNull { it.getOrNull() }

        if (heroes.isNotEmpty()) {
            Result.success(heroes)
        } else {
            val firstError = results.firstOrNull { it.isFailure }?.exceptionOrNull()
                ?: RuntimeException("Failed to load any initial heroes")
            Result.failure(firstError)
        }
    }
}
