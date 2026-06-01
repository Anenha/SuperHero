package com.anenha.superhero.domain.repository

import com.anenha.superhero.domain.model.SuperHero

interface SuperHeroRepository {
    suspend fun getHero(id: String): Result<SuperHero>
    suspend fun searchHeroes(query: String): Result<List<SuperHero>>
}
