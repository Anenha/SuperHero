package com.anenha.superhero.core.network.service

import com.anenha.superhero.core.network.model.SearchResponseDto
import com.anenha.superhero.core.network.model.SuperHeroDto
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroService {
    @GET("{id}")
    suspend fun getHero(@Path("id") id: String): SuperHeroDto

    @GET("search/{name}")
    suspend fun searchHeroes(@Path("name") name: String): SearchResponseDto
}
