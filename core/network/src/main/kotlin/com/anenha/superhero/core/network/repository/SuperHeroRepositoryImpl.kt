package com.anenha.superhero.core.network.repository

import com.anenha.superhero.core.network.service.SuperHeroService
import com.anenha.superhero.core.network.model.toDomain
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.repository.SuperHeroRepository
import javax.inject.Inject

class SuperHeroRepositoryImpl @Inject constructor(
    private val service: SuperHeroService
) : SuperHeroRepository {

    override suspend fun getHero(id: String): Result<SuperHero> {
        return runCatching {
            service.getHero(id).toDomain()
        }
    }

    override suspend fun searchHeroes(query: String): Result<List<SuperHero>> {
        return runCatching {
            val response = service.searchHeroes(query)
            if (response.response == "success") {
                response.results?.map { it.toDomain() } ?: emptyList()
            } else {
                throw RuntimeException(response.error ?: "Search failed")
            }
        }
    }
}