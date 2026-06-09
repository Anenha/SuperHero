package com.anenha.superhero.features.comparison.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.anenha.superhero.core.designsystem.component.HexagonImageFrame
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.Appearance
import com.anenha.superhero.domain.model.Biography
import com.anenha.superhero.domain.model.Connections
import com.anenha.superhero.domain.model.PowerStats
import com.anenha.superhero.domain.model.HeroAlignment
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.model.Work
import com.anenha.superhero.core.designsystem.R as DesignSystemR

@Composable
fun HexagonPortrait(
    hero: SuperHero,
    borderColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                id = when (hero.alignment) {
                    HeroAlignment.HERO -> DesignSystemR.string.tag_hero
                    HeroAlignment.VILLAIN -> DesignSystemR.string.tag_villain
                    HeroAlignment.NEUTRAL -> DesignSystemR.string.tag_neutral
                }
            ),
            style = MaterialTheme.typography.labelSmall,
            color = borderColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        HexagonImageFrame(
            borderColor = borderColor,
            modifier = Modifier.size(100.dp)
        ) {
            AsyncImage(
                model = hero.imageUrl,
                contentDescription = hero.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = hero.name.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

private fun createMockHero(id: String, name: String, isVillain: Boolean): SuperHero {
    return SuperHero(
        id = id,
        name = name,
        powerstats = PowerStats(
            intelligence = 80,
            strength = 70,
            speed = 60,
            durability = 85,
            power = 90,
            combat = 75
        ),
        biography = Biography(
            fullName = name,
            alterEgos = "No alter egos",
            aliases = listOf(name),
            placeOfBirth = "Gotham",
            firstAppearance = "Unknown",
            publisher = "Vanguard Publisher",
            alignment = if (isVillain) "bad" else "good"
        ),
        appearance = Appearance(
            gender = "Male",
            race = "Human",
            height = "188 cm",
            weight = "95 kg",
            eyeColor = "Blue",
            hairColor = "Black"
        ),
        work = Work(occupation = "Hero", base = "Vanguard Base"),
        connections = Connections(groupAffiliation = "Justice League", relatives = "None"),
        imageUrl = ""
    )
}

@Preview(name = "Hexagon Portrait - Hero")
@Composable
private fun HeroHexagonPortraitPreview() {
    val mockHero = createMockHero("1", "Batman", false)
    VanguardKineticTheme {
        HexagonPortrait(
            hero = mockHero,
            borderColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(name = "Hexagon Portrait - Villain")
@Composable
private fun VillainHexagonPortraitPreview() {
    val mockVillain = createMockHero("2", "Joker", true)
    VanguardKineticTheme {
        HexagonPortrait(
            hero = mockVillain,
            borderColor = MaterialTheme.colorScheme.secondary
        )
    }
}
