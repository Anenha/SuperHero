package com.anenha.superhero.domain.usecase

import com.anenha.superhero.domain.repository.FakeSuperHeroRepository
import com.anenha.superhero.domain.repository.createFakeHero
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetHeroDetailsUseCaseTest {

    private val repository = FakeSuperHeroRepository()
    private val usecase = GetHeroDetailsUseCase(repository)

    @Test
    fun `getHero returns success when hero exists in repository`() = runTest {
        // Arrange
        val hero = createFakeHero(id = "1", name = "Batman")
        repository.addHeroes(hero)

        // Act
        val result = usecase("1")

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(hero, result.getOrThrow())
    }

    @Test
    fun `getHero returns failure when hero does not exist in repository`() = runTest {
        // Act
        val result = usecase("2")

        // Assert
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NoSuchElementException)
    }

    @Test
    fun `getHero returns failure when repository returns error`() = runTest {
        // Arrange
        val exception = RuntimeException("Connection issue")
        repository.setShouldReturnError(true, exception)

        // Act
        val result = usecase("1")

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
