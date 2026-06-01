package com.anenha.superhero.core.network.model

import com.anenha.superhero.domain.model.Appearance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppearanceDto(
    val gender: String? = null,
    val race: String? = null,
    val height: List<String> = emptyList(),
    val weight: List<String> = emptyList(),
    @SerialName("eye-colors") val eyeColor: String? = null,
    @SerialName("eye-color") val eyeColorAlt: String? = null,
    @SerialName("hair-color") val hairColor: String? = null,
)

fun AppearanceDto.toDomain() = Appearance(
    gender = gender ?: "",
    race = race ?: "",
    height = height.joinToString(" / "),
    weight = weight.joinToString(" / "),
    eyeColor = eyeColor ?: eyeColorAlt ?: "",
    hairColor = hairColor ?: ""
)
