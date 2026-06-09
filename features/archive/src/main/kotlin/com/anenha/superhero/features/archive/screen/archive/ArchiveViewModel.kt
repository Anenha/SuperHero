package com.anenha.superhero.features.archive.screen.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.usecase.GetRandomInitialHeroesUseCase
import com.anenha.superhero.domain.usecase.SearchHeroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val getRandomInitialHeroes: GetRandomInitialHeroesUseCase,
    private val searchHeroes: SearchHeroesUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _uiState = MutableStateFlow<ArchiveUiState>(ArchiveUiState.Loading)
    val uiState: StateFlow<ArchiveUiState> = _uiState

    private var initialHeroes: List<SuperHero> = emptyList()

    init {
        loadInitialHeroes()
        observeSearchQuery()
    }

    fun loadInitialHeroes() {
        viewModelScope.launch {
            _uiState.value = ArchiveUiState.Loading
            getRandomInitialHeroes()
                .onSuccess { heroes ->
                    initialHeroes = heroes
                    _uiState.value = ArchiveUiState.Success(heroes)
                }
                .onFailure { error ->
                    _uiState.value = ArchiveUiState.Error(error.localizedMessage ?: "Unknown error")
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun observeSearchQuery() {
        searchQuery
            .debounce(300.milliseconds)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.trim().length >= 3) {
                    performSearch(query)
                } else {
                    if (initialHeroes.isNotEmpty()) {
                        _uiState.value = ArchiveUiState.Success(initialHeroes)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun performSearch(query: String) {
        _uiState.value = ArchiveUiState.Loading
        searchHeroes(query)
            .onSuccess { results ->
                _uiState.value = ArchiveUiState.Success(results)
            }
            .onFailure { error ->
                _uiState.value = ArchiveUiState.Error(error.localizedMessage ?: "Search failed")
            }
    }
}
