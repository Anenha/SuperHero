package com.anenha.superhero.domain.usecase

import com.anenha.superhero.domain.repository.FakeSuperHeroRepository
import com.anenha.superhero.domain.repository.createFakeHero
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchHeroesUseCaseTest {

    private val repository = FakeSuperHeroRepository()
    private val usecase = SearchHeroesUseCase(repository)

    @Test
    fun `search returns empty list when query is less than 3 characters`() = runTest {
        // Arrange
        val hero = createFakeHero(id = "1", name = "Batman")
        repository.addHeroes(hero)
        // Set repository to return error so that if it is called, it would fail
        repository.setShouldReturnError(true)

        // Act
        val result = usecase("Ba")

        // Assert
        assertTrue(result.isSuccess)
        assertTrue(result.getOrThrow().isEmpty())
    }

    @Test
    fun `search returns matching heroes when query is valid and matches exist`() = runTest {
        // Arrange
        val hero1 = createFakeHero(id = "1", name = "Batman")
        val hero2 = createFakeHero(id = "2", name = "Superman")
        val hero3 = createFakeHero(id = "3", name = "Batgirl")
        repository.addHeroes(hero1, hero2, hero3)

        // Act
        val result = usecase("  bat  ") // should trim and match ignoring case

        // Assert
        assertTrue(result.isSuccess)
        val matches = result.getOrThrow()
        assertEquals(2, matches.size)
        assertTrue(matches.contains(hero1))
        assertTrue(matches.contains(hero3))
    }

    @Test
    fun `search returns empty list when query is valid but no matches exist`() = runTest {
        // Arrange
        val hero = createFakeHero(id = "1", name = "Batman")
        repository.addHeroes(hero)

        // Act
        val result = usecase("Spiderman")

        // Assert
        assertTrue(result.isSuccess)
        assertTrue(result.getOrThrow().isEmpty())
    }

    @Test
    fun `search returns failure when repository returns error`() = runTest {
        // Arrange
        val exception = RuntimeException("Database error")
        repository.setShouldReturnError(true, exception)

        // Act
        val result = usecase("Batman")

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
