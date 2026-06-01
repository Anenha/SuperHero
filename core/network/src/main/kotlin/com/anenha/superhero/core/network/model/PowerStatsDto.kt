package com.anenha.superhero.core.network.model

import com.anenha.superhero.domain.model.PowerStats
import kotlinx.serialization.Serializable

@Serializable
data class PowerStatsDto(
    val intelligence: String = "0",
    val strength: String = "0",
    val speed: String = "0",
    val durability: String = "0",
    val power: String = "0",
    val combat: String = "0",
)

fun PowerStatsDto.toDomain() = PowerStats(
    intelligence = intelligence.toIntOrNull() ?: 0,
    strength = strength.toIntOrNull() ?: 0,
    speed = speed.toIntOrNull() ?: 0,
    durability = durability.toIntOrNull() ?: 0,
    power = power.toIntOrNull() ?: 0,
    combat = combat.toIntOrNull() ?: 0
)
