package com.anenha.superhero.domain.model

data class SuperHero(
    val id: String,
    val name: String,
    val powerstats: PowerStats,
    val biography: Biography,
    val appearance: Appearance,
    val work: Work,
    val connections: Connections,
    val imageUrl: String
) {
    val isHero: Boolean
        get() = biography.alignment.equals("good", ignoreCase = true)

    val isVillain: Boolean
        get() = biography.alignment.equals("bad", ignoreCase = true)
}
