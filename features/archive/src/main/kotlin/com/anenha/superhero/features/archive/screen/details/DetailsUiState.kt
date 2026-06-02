package com.anenha.superhero.features.archive.screen.details

import com.anenha.superhero.domain.model.SuperHero

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Success(val hero: SuperHero) : DetailsUiState
    data class Error(val message: String) : DetailsUiState
}
