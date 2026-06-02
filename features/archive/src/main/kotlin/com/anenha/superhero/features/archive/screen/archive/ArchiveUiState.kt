package com.anenha.superhero.features.archive.screen.archive

import com.anenha.superhero.domain.model.SuperHero

sealed interface ArchiveUiState {
    data object Loading : ArchiveUiState
    data class Success(val heroes: List<SuperHero>) : ArchiveUiState
    data class Error(val message: String) : ArchiveUiState
}
