package com.anenha.superhero.features.comparison.screen.comparison

import com.anenha.superhero.domain.model.SuperHero

sealed interface ComparisonUiState {
    data object Loading : ComparisonUiState
    data class Success(val hero1: SuperHero, val hero2: SuperHero) : ComparisonUiState
    data class Error(val message: String) : ComparisonUiState
}
