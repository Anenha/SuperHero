package com.anenha.superhero.core.network.model

import com.anenha.superhero.domain.model.Connections
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectionsDto(
    @SerialName("group-affiliation") val groupAffiliation: String? = null,
    val relatives: String? = null,
)

fun ConnectionsDto.toDomain() = Connections(
    groupAffiliation = groupAffiliation ?: "",
    relatives = relatives ?: ""
)
