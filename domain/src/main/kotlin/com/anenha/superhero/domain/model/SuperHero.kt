package com.anenha.superhero.domain.model

enum class HeroAlignment {
    HERO, VILLAIN, NEUTRAL
}

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
    val alignment: HeroAlignment
        get() = when {
            biography.alignment.equals("good", ignoreCase = true) -> HeroAlignment.HERO
            biography.alignment.equals("bad", ignoreCase = true) -> HeroAlignment.VILLAIN
            else -> HeroAlignment.NEUTRAL
        }
}
