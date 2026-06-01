package com.anenha.superhero.core.network.model

import com.anenha.superhero.domain.model.SuperHero
import kotlinx.serialization.Serializable

@Serializable
data class SuperHeroDto(
    val id: String,
    val name: String,
    val powerstats: PowerStatsDto = PowerStatsDto(),
    val biography: BiographyDto = BiographyDto(),
    val appearance: AppearanceDto = AppearanceDto(),
    val work: WorkDto = WorkDto(),
    val connections: ConnectionsDto = ConnectionsDto(),
    val image: ImageDto = ImageDto(),
)

fun SuperHeroDto.toDomain() = SuperHero(
    id = id,
    name = name,
    powerstats = powerstats.toDomain(),
    biography = biography.toDomain(),
    appearance = appearance.toDomain(),
    work = work.toDomain(),
    connections = connections.toDomain(),
    imageUrl = image.url
)
