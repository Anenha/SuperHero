package com.anenha.superhero.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val url: String = ""
)
