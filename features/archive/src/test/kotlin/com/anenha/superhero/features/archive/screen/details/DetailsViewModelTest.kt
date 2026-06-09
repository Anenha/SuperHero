package com.anenha.superhero.features.archive.screen.details

import com.anenha.superhero.domain.repository.FakeSuperHeroRepository
import com.anenha.superhero.domain.repository.createFakeHero
import com.anenha.superhero.domain.usecase.GetHeroDetailsUseCase
import com.anenha.superhero.domain.usecase.SearchHeroesUseCase
import com.anenha.superhero.domain.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private lateinit var repository: FakeSuperHeroRepository
    private lateinit var getHeroDetails: GetHeroDetailsUseCase
    private lateinit var searchHeroes: SearchHeroesUseCase

    @Before
    fun setUp() {
        repository = FakeSuperHeroRepository()
        getHeroDetails = GetHeroDetailsUseCase(repository)
        searchHeroes = SearchHeroesUseCase(repository)
    }

    @Test
    fun `init loads hero details and updates uiState to Success`() = runTest(testDispatcher) {
        // Arrange
        val hero = createFakeHero(id = "1", name = "Batman")
        repository.addHeroes(hero)

        // Act
        val viewModel = DetailsViewModel(getHeroDetails, searchHeroes, "1")
        assertEquals(DetailsUiState.Loading, viewModel.uiState.value)
        runCurrent()

        // Assert
        assertTrue(viewModel.uiState.value is DetailsUiState.Success)
        val state = viewModel.uiState.value as DetailsUiState.Success
        assertEquals("1", state.hero.id)
        assertEquals("Batman", state.hero.name)
    }

    @Test
    fun `init loads hero details and updates uiState to Error on failure`() = runTest(testDispatcher) {
        // Arrange
        // (Empty repository will result in NoSuchElementException on getHero)

        // Act
        val viewModel = DetailsViewModel(getHeroDetails, searchHeroes, "1")
        runCurrent()

        // Assert
        assertTrue(viewModel.uiState.value is DetailsUiState.Error)
        val state = viewModel.uiState.value as DetailsUiState.Error
        assertTrue(state.message.contains("not found"))
    }

    @Test
    fun `onCompareQueryChanged updates compareQuery state flow`() = runTest(testDispatcher) {
        // Arrange
        val hero = createFakeHero(id = "1", name = "Batman")
        repository.addHeroes(hero)
        val viewModel = DetailsViewModel(getHeroDetails, searchHeroes, "1")
        runCurrent()

        // Act
        viewModel.onCompareQueryChanged("Super")

        // Assert
        assertEquals("Super", viewModel.compareQuery.value)
    }

    @Test
    fun `observeCompareQuery triggers search when query is valid and updates dropdown results`() = runTest(testDispatcher) {
        // Arrange
        val hero = createFakeHero(id = "1", name = "Batman")
        val comparedHero = createFakeHero(id = "2", name = "Superman")
        repository.addHeroes(hero, comparedHero)
        val viewModel = DetailsViewModel(getHeroDetails, searchHeroes, "1")
        runCurrent()

        // Act
        viewModel.onCompareQueryChanged("Super")
        runCurrent()
        // verify search hasn't triggered yet due to debounce
        assertFalse(viewModel.isDropdownLoading.value)
        assertTrue(viewModel.dropdownResults.value.isEmpty())

        // Advance to trigger debounce
        advanceTimeBy(301)
        runCurrent()

        // Assert
        assertFalse(viewModel.isDropdownLoading.value)
        assertEquals(1, viewModel.dropdownResults.value.size)
        assertEquals("Superman", viewModel.dropdownResults.value[0].name)
    }

    @Test
    fun `observeCompareQuery does not trigger search and clears dropdown when query is less than 3 chars`() = runTest(testDispatcher) {
        // Arrange
        val hero = createFakeHero(id = "1", name = "Batman")
        val comparedHero = createFakeHero(id = "2", name = "Superman")
        repository.addHeroes(hero, comparedHero)
        val viewModel = DetailsViewModel(getHeroDetails, searchHeroes, "1")
        runCurrent()

        // Perform valid search first
        viewModel.onCompareQueryChanged("Super")
        advanceTimeBy(301)
        runCurrent()
        assertEquals(1, viewModel.dropdownResults.value.size)

        // Act - change query to < 3 chars
        viewModel.onCompareQueryChanged("Su")
        advanceTimeBy(301)
        runCurrent()

        // Assert
        assertTrue(viewModel.dropdownResults.value.isEmpty())
        assertFalse(viewModel.isDropdownLoading.value)
    }

    @Test
    fun `observeCompareQuery clears dropdown results when search fails`() = runTest(testDispatcher) {
        // Arrange
        val hero = createFakeHero(id = "1", name = "Batman")
        repository.addHeroes(hero)
        val viewModel = DetailsViewModel(getHeroDetails, searchHeroes, "1")
        runCurrent()

        // Make search fail
        repository.setShouldReturnError(true, RuntimeException("Search failed"))

        // Act
        viewModel.onCompareQueryChanged("Super")
        advanceTimeBy(301)
        runCurrent()

        // Assert
        assertTrue(viewModel.dropdownResults.value.isEmpty())
        assertFalse(viewModel.isDropdownLoading.value)
    }

    @Test
    fun `clearDropdown clears search query and dropdown results`() = runTest(testDispatcher) {
        // Arrange
        val hero = createFakeHero(id = "1", name = "Batman")
        val comparedHero = createFakeHero(id = "2", name = "Superman")
        repository.addHeroes(hero, comparedHero)
        val viewModel = DetailsViewModel(getHeroDetails, searchHeroes, "1")
        runCurrent()

        viewModel.onCompareQueryChanged("Super")
        advanceTimeBy(301)
        runCurrent()
        assertEquals("Super", viewModel.compareQuery.value)
        assertEquals(1, viewModel.dropdownResults.value.size)

        // Act
        viewModel.clearDropdown()

        // Assert
        assertEquals("", viewModel.compareQuery.value)
        assertTrue(viewModel.dropdownResults.value.isEmpty())
    }
}
