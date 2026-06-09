package com.anenha.superhero.domain.usecase

import com.anenha.superhero.domain.repository.FakeSuperHeroRepository
import com.anenha.superhero.domain.repository.createFakeHero
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetRandomInitialHeroesUseCaseTest {

    @Test
    fun `getRandomInitialHeroes fetches 10 unique characters concurrently and returns success`() = runTest {
        // Arrange
        val repository = FakeSuperHeroRepository().apply {
            addHeroes((1..731).map { createFakeHero(it.toString()) })
        }
        val usecase = GetRandomInitialHeroesUseCase(repository)

        // Act
        val result = usecase()

        // Assert
        assertTrue(result.isSuccess)
        val heroes = result.getOrThrow()
        assertEquals(10, heroes.size)
        
        // Verify all 10 have unique IDs
        val uniqueIds = heroes.map { it.id }.toSet()
        assertEquals(10, uniqueIds.size)
        
        // Verify they are within range 1 to 731
        uniqueIds.forEach { id ->
            val num = id.toInt()
            assertTrue(num in 1..731)
        }
    }

    @Test
    fun `getRandomInitialHeroes returns failure when all fetches fail`() = runTest {
        // Arrange
        val repository = FakeSuperHeroRepository().apply {
            setShouldReturnError(true, RuntimeException("Network error"))
        }
        val usecase = GetRandomInitialHeroesUseCase(repository)

        // Act
        val result = usecase()

        // Assert
        assertTrue(result.isFailure)
    }
}
