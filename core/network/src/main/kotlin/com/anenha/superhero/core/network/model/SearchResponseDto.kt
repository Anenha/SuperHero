package com.anenha.superhero.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    val response: String,
    @SerialName("results-for") val resultsFor: String? = null,
    val results: List<SuperHeroDto>? = null,
    val error: String? = null,
)
