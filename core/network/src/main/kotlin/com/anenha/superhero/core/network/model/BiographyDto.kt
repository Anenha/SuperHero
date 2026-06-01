package com.anenha.superhero.core.network.model

import com.anenha.superhero.domain.model.Biography
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BiographyDto(
    @SerialName("full-name") val fullName: String? = null,
    @SerialName("alter-egos") val alterEgos: String? = null,
    val aliases: List<String> = emptyList(),
    @SerialName("place-of-birth") val placeOfBirth: String? = null,
    @SerialName("first-appearance") val firstAppearance: String? = null,
    val publisher: String? = null,
    val alignment: String? = null,
)

fun BiographyDto.toDomain() = Biography(
    fullName = fullName ?: "",
    alterEgos = alterEgos ?: "",
    aliases = aliases.filter { it.isNotBlank() },
    placeOfBirth = placeOfBirth ?: "",
    firstAppearance = firstAppearance ?: "",
    publisher = publisher ?: "",
    alignment = alignment ?: "neutral"
)
