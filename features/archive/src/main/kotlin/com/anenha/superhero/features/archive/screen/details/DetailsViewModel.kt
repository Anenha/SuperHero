package com.anenha.superhero.features.archive.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.usecase.GetHeroDetailsUseCase
import com.anenha.superhero.domain.usecase.SearchHeroesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    private val getHeroDetails: GetHeroDetailsUseCase,
    private val searchHeroes: SearchHeroesUseCase,
    @Assisted private val heroId: String
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(heroId: String): DetailsViewModel
    }

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState

    private val _compareQuery = MutableStateFlow("")
    val compareQuery: StateFlow<String> = _compareQuery

    private val _dropdownResults = MutableStateFlow<List<SuperHero>>(emptyList())
    val dropdownResults: StateFlow<List<SuperHero>> = _dropdownResults

    private val _isDropdownLoading = MutableStateFlow(false)
    val isDropdownLoading: StateFlow<Boolean> = _isDropdownLoading

    init {
        loadHeroDetails()
        observeCompareQuery()
    }

    fun loadHeroDetails() {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            getHeroDetails(heroId)
                .onSuccess { hero ->
                    _uiState.value = DetailsUiState.Success(hero)
                }
                .onFailure { error ->
                    _uiState.value = DetailsUiState.Error(error.localizedMessage ?: "Failed to load details")
                }
        }
    }

    fun onCompareQueryChanged(query: String) {
        _compareQuery.value = query
    }

    private fun observeCompareQuery() {
        compareQuery
            .debounce(300.milliseconds)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.trim().length >= 3) {
                    _isDropdownLoading.value = true
                    searchHeroes(query)
                        .onSuccess { results ->
                            _dropdownResults.value = results
                            _isDropdownLoading.value = false
                        }
                        .onFailure {
                            _dropdownResults.value = emptyList()
                            _isDropdownLoading.value = false
                        }
                } else {
                    _dropdownResults.value = emptyList()
                    _isDropdownLoading.value = false
                }
            }
            .launchIn(viewModelScope)
    }

    fun clearDropdown() {
        _compareQuery.value = ""
        _dropdownResults.value = emptyList()
    }
}
