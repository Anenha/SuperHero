package com.anenha.superhero.domain.usecase

import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.repository.SuperHeroRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetRandomInitialHeroesUseCase(private val repository: SuperHeroRepository) {
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
