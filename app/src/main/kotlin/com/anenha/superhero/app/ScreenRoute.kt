package com.anenha.superhero.app

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenRoute : NavKey

@Serializable
data object ArchiveRoute : ScreenRoute

@Serializable
data class DetailsRoute(val id: String) : ScreenRoute

@Serializable
data class ComparisonRoute(val id1: String, val id2: String) : ScreenRoute
