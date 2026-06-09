package com.anenha.superhero.domain.repository

import com.anenha.superhero.domain.model.Appearance
import com.anenha.superhero.domain.model.Biography
import com.anenha.superhero.domain.model.Connections
import com.anenha.superhero.domain.model.PowerStats
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.model.Work

/**
 * A helper function to create a [SuperHero] with default mock values for testing.
 */
fun createFakeHero(
    id: String,
    name: String = "Hero $id",
    alignment: String = "good",
    imageUrl: String = "http://example.com/image.jpg"
): SuperHero {
    return SuperHero(
        id = id,
        name = name,
        powerstats = PowerStats(
            intelligence = 50,
            strength = 50,
            speed = 50,
            durability = 50,
            power = 50,
            combat = 50
        ),
        biography = Biography(
            fullName = "",
            alterEgos = "",
            aliases = emptyList(),
            placeOfBirth = "",
            firstAppearance = "",
            publisher = "",
            alignment = alignment
        ),
        appearance = Appearance(
            gender = "",
            race = "",
            height = "",
            weight = "",
            eyeColor = "",
            hairColor = ""
        ),
        work = Work(
            occupation = "",
            base = ""
        ),
        connections = Connections(
            groupAffiliation = "",
            relatives = ""
        ),
        imageUrl = imageUrl
    )
}

/**
 * A fake implementation of [SuperHeroRepository] to be used in unit tests.
 */
class FakeSuperHeroRepository : SuperHeroRepository {
    private val heroes = mutableMapOf<String, SuperHero>()
    private var shouldReturnError = false
    private var errorException: Throwable = RuntimeException("Fake repository error")

    fun addHeroes(vararg hero: SuperHero) {
        hero.forEach { heroes[it.id] = it }
    }

    fun addHeroes(heroesList: List<SuperHero>) {
        heroesList.forEach { heroes[it.id] = it }
    }

    fun setShouldReturnError(shouldError: Boolean, exception: Throwable = RuntimeException("Fake repository error")) {
        shouldReturnError = shouldError
        errorException = exception
    }

    fun clear() {
        heroes.clear()
        shouldReturnError = false
        errorException = RuntimeException("Fake repository error")
    }

    override suspend fun getHero(id: String): Result<SuperHero> {
        if (shouldReturnError) {
            return Result.failure(errorException)
        }
        val hero = heroes[id] ?: return Result.failure(NoSuchElementException("Hero with id $id not found"))
        return Result.success(hero)
    }

    override suspend fun searchHeroes(query: String): Result<List<SuperHero>> {
        if (shouldReturnError) {
            return Result.failure(errorException)
        }
        val results = heroes.values.filter {
            it.name.contains(query, ignoreCase = true)
        }
        return Result.success(results)
    }
}
