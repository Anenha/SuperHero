package com.anenha.superhero.features.archive.screen.archive

import com.anenha.superhero.domain.repository.FakeSuperHeroRepository
import com.anenha.superhero.domain.repository.createFakeHero
import com.anenha.superhero.domain.usecase.GetRandomInitialHeroesUseCase
import com.anenha.superhero.domain.usecase.SearchHeroesUseCase
import com.anenha.superhero.domain.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArchiveViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private lateinit var repository: FakeSuperHeroRepository
    private lateinit var getRandomInitialHeroes: GetRandomInitialHeroesUseCase
    private lateinit var searchHeroes: SearchHeroesUseCase

    @Before
    fun setUp() {
        repository = FakeSuperHeroRepository().apply {
            addHeroes((1..731).map { createFakeHero(id = it.toString()) })
        }
        getRandomInitialHeroes = GetRandomInitialHeroesUseCase(repository)
        searchHeroes = SearchHeroesUseCase(repository)
    }

    @Test
    fun `init loads initial heroes and updates uiState to Success`() = runTest(testDispatcher) {
        // Act
        val viewModel = ArchiveViewModel(getRandomInitialHeroes, searchHeroes)
        assertEquals(ArchiveUiState.Loading, viewModel.uiState.value)
        runCurrent()

        // Assert
        assertTrue(viewModel.uiState.value is ArchiveUiState.Success)
        val state = viewModel.uiState.value as ArchiveUiState.Success
        assertEquals(10, state.heroes.size)
    }

    @Test
    fun `init failure when loading initial heroes updates uiState to Error`() = runTest(testDispatcher) {
        // Arrange
        repository.clear()
        repository.setShouldReturnError(true, RuntimeException("Database error"))

        // Act
        val viewModel = ArchiveViewModel(getRandomInitialHeroes, searchHeroes)
        runCurrent()

        // Assert
        assertTrue(viewModel.uiState.value is ArchiveUiState.Error)
        val state = viewModel.uiState.value as ArchiveUiState.Error
        assertEquals("Database error", state.message)
    }

    @Test
    fun `onSearchQueryChanged updates searchQuery state flow`() = runTest(testDispatcher) {
        // Arrange
        val viewModel = ArchiveViewModel(getRandomInitialHeroes, searchHeroes)
        runCurrent()

        // Act
        viewModel.onSearchQueryChanged("Batman")

        // Assert
        assertEquals("Batman", viewModel.searchQuery.value)
    }

    @Test
    fun `observeSearchQuery triggers search when query is valid and matches exist`() = runTest(testDispatcher) {
        // Arrange
        val hero = createFakeHero(id = "1000", name = "Batman")
        repository.addHeroes(hero)
        val viewModel = ArchiveViewModel(getRandomInitialHeroes, searchHeroes)
        runCurrent() // complete init loading

        // Act
        viewModel.onSearchQueryChanged("Bat")
        // Before debounce timeout, search is not triggered
        runCurrent()
        assertTrue(viewModel.uiState.value is ArchiveUiState.Success)
        // Check that it still has the initial 10 heroes
        assertEquals(10, (viewModel.uiState.value as ArchiveUiState.Success).heroes.size)

        // Advance time to pass debounce (300ms)
        advanceTimeBy(301)
        runCurrent()

        // Assert
        assertTrue(viewModel.uiState.value is ArchiveUiState.Success)
        val state = viewModel.uiState.value as ArchiveUiState.Success
        assertEquals(1, state.heroes.size)
        assertEquals("Batman", state.heroes[0].name)
    }

    @Test
    fun `observeSearchQuery does not trigger search when query is less than 3 chars and reverts to initial heroes`() = runTest(testDispatcher) {
        // Arrange
        val viewModel = ArchiveViewModel(getRandomInitialHeroes, searchHeroes)
        runCurrent() // load initial heroes

        // Perform search first
        viewModel.onSearchQueryChanged("HeroDoesNotExist")
        advanceTimeBy(301)
        runCurrent()
        // verify search completed (empty success results)
        assertTrue(viewModel.uiState.value is ArchiveUiState.Success)
        assertTrue((viewModel.uiState.value as ArchiveUiState.Success).heroes.isEmpty())

        // Act - change query to less than 3 characters
        viewModel.onSearchQueryChanged("He")
        advanceTimeBy(301)
        runCurrent()

        // Assert - should revert to initial heroes list (10 heroes)
        assertTrue(viewModel.uiState.value is ArchiveUiState.Success)
        val state = viewModel.uiState.value as ArchiveUiState.Success
        assertEquals(10, state.heroes.size)
    }

    @Test
    fun `observeSearchQuery debounces consecutive rapid query changes`() = runTest(testDispatcher) {
        // Arrange
        val batman = createFakeHero(id = "1001", name = "Batman")
        val superman = createFakeHero(id = "1002", name = "Superman")
        repository.addHeroes(batman, superman)
        val viewModel = ArchiveViewModel(getRandomInitialHeroes, searchHeroes)
        runCurrent()

        // Act - type query rapidly
        viewModel.onSearchQueryChanged("Bat")
        advanceTimeBy(200) // less than 300ms
        runCurrent()

        viewModel.onSearchQueryChanged("Super")
        advanceTimeBy(200) // less than 300ms from last, but 400ms from start
        runCurrent()

        // At 400ms, no search has completed since we keep updating before 300ms elapsed
        assertTrue(viewModel.uiState.value is ArchiveUiState.Success)
        assertEquals(10, (viewModel.uiState.value as ArchiveUiState.Success).heroes.size)

        // Now wait full 300ms for "Super"
        advanceTimeBy(101) // total 301ms from "Super" query change
        runCurrent()

        // Assert - search should be performed with the last query "Super"
        assertTrue(viewModel.uiState.value is ArchiveUiState.Success)
        val state = viewModel.uiState.value as ArchiveUiState.Success
        assertEquals(1, state.heroes.size)
        assertEquals("Superman", state.heroes[0].name)
    }

    @Test
    fun `search failure updates uiState to Error`() = runTest(testDispatcher) {
        // Arrange
        val viewModel = ArchiveViewModel(getRandomInitialHeroes, searchHeroes)
        runCurrent() // complete init load

        repository.setShouldReturnError(true, RuntimeException("Search API error"))

        // Act
        viewModel.onSearchQueryChanged("Batman")
        advanceTimeBy(301)
        runCurrent()

        // Assert
        assertTrue(viewModel.uiState.value is ArchiveUiState.Error)
        val state = viewModel.uiState.value as ArchiveUiState.Error
        assertEquals("Search API error", state.message)
    }
}
