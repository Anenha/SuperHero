package com.anenha.superhero.features.comparison.screen.comparison

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anenha.superhero.domain.usecase.GetHeroDetailsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ComparisonViewModel.Factory::class)
class ComparisonViewModel @AssistedInject constructor(
    private val getHeroDetails: GetHeroDetailsUseCase,
    @Assisted("id1") private val id1: String,
    @Assisted("id2") private val id2: String
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("id1") id1: String,
            @Assisted("id2") id2: String
        ): ComparisonViewModel
    }

    private val _uiState = MutableStateFlow<ComparisonUiState>(ComparisonUiState.Loading)
    val uiState: StateFlow<ComparisonUiState> = _uiState

    init {
        loadComparisonData()
    }

    fun loadComparisonData() {
        viewModelScope.launch {
            _uiState.value = ComparisonUiState.Loading
            
            // Run detail fetches in parallel
            val fetch1 = async { getHeroDetails(id1) }
            val fetch2 = async { getHeroDetails(id2) }

            val result1 = fetch1.await()
            val result2 = fetch2.await()

            if (result1.isSuccess && result2.isSuccess) {
                _uiState.value = ComparisonUiState.Success(
                    hero1 = result1.getOrThrow(),
                    hero2 = result2.getOrThrow()
                )
            } else {
                val errorMsg = listOfNotNull(
                    result1.exceptionOrNull()?.localizedMessage,
                    result2.exceptionOrNull()?.localizedMessage
                ).joinToString(" | ").ifEmpty { "Failed to load comparison data" }
                
                _uiState.value = ComparisonUiState.Error(errorMsg)
            }
        }
    }
}
