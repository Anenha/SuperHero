package com.anenha.superhero.features.comparison.screen.comparison

import com.anenha.superhero.domain.repository.FakeSuperHeroRepository
import com.anenha.superhero.domain.repository.createFakeHero
import com.anenha.superhero.domain.usecase.GetHeroDetailsUseCase
import com.anenha.superhero.domain.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ComparisonViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private lateinit var repository: FakeSuperHeroRepository
    private lateinit var getHeroDetails: GetHeroDetailsUseCase

    @Before
    fun setUp() {
        repository = FakeSuperHeroRepository()
        getHeroDetails = GetHeroDetailsUseCase(repository)
    }

    @Test
    fun `init loads details for both heroes and updates uiState to Success`() = runTest(testDispatcher) {
        // Arrange
        val hero1 = createFakeHero(id = "1", name = "Batman")
        val hero2 = createFakeHero(id = "2", name = "Superman")
        repository.addHeroes(hero1, hero2)

        // Act
        val viewModel = ComparisonViewModel(getHeroDetails, "1", "2")
        assertEquals(ComparisonUiState.Loading, viewModel.uiState.value)
        runCurrent()

        // Assert
        assertTrue(viewModel.uiState.value is ComparisonUiState.Success)
        val state = viewModel.uiState.value as ComparisonUiState.Success
        assertEquals("Batman", state.hero1.name)
        assertEquals("Superman", state.hero2.name)
    }

    @Test
    fun `init updates uiState to Error when one hero load fails`() = runTest(testDispatcher) {
        // Arrange
        val hero1 = createFakeHero(id = "1", name = "Batman")
        repository.addHeroes(hero1)
        // hero 2 is missing, which will cause NoSuchElementException for id 2

        // Act
        val viewModel = ComparisonViewModel(getHeroDetails, "1", "2")
        runCurrent()

        // Assert
        assertTrue(viewModel.uiState.value is ComparisonUiState.Error)
        val state = viewModel.uiState.value as ComparisonUiState.Error
        assertTrue(state.message.contains("Hero with id 2 not found"))
    }

    @Test
    fun `init updates uiState to Error with combined messages when both hero loads fail`() = runTest(testDispatcher) {
        // Arrange
        // repository is empty, both fail

        // Act
        val viewModel = ComparisonViewModel(getHeroDetails, "1", "2")
        runCurrent()

        // Assert
        assertTrue(viewModel.uiState.value is ComparisonUiState.Error)
        val state = viewModel.uiState.value as ComparisonUiState.Error
        assertTrue(state.message.contains("Hero with id 1 not found"))
        assertTrue(state.message.contains("Hero with id 2 not found"))
        assertTrue(state.message.contains(" | "))
    }
}
