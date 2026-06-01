package com.anenha.superhero.domain.model

data class Biography(
    val fullName: String,
    val alterEgos: String,
    val aliases: List<String>,
    val placeOfBirth: String,
    val firstAppearance: String,
    val publisher: String,
    val alignment: String // "good", "bad", "neutral" etc.
)
