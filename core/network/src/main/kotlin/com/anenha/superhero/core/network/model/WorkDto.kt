package com.anenha.superhero.core.network.model

import com.anenha.superhero.domain.model.Work
import kotlinx.serialization.Serializable

@Serializable
data class WorkDto(
    val occupation: String? = null,
    val base: String? = null,
)

fun WorkDto.toDomain() = Work(
    occupation = occupation ?: "",
    base = base ?: ""
)
