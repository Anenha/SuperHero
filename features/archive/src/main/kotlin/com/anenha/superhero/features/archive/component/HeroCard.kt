package com.anenha.superhero.features.archive.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.Appearance
import com.anenha.superhero.domain.model.Biography
import com.anenha.superhero.domain.model.Connections
import com.anenha.superhero.domain.model.PowerStats
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.model.Work
import com.anenha.superhero.features.archive.R
import com.anenha.superhero.core.designsystem.R as DesignSystemR

@Composable
fun HeroCard(
    hero: SuperHero,
    onSelected: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
            .clickable(onClick = onSelected),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Character Portrait image
            with(sharedTransitionScope) {
                AsyncImage(
                    model = hero.imageUrl,
                    contentDescription = hero.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = "hero_image_${hero.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
            }

            // Dark gradient overlay at the bottom for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f)
                            ),
                            startY = 350f
                        )
                    )
            )

            // Top-right alignment tag
            val tagInfo = when {
                hero.isHero -> Triple(
                    DesignSystemR.string.tag_hero,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onPrimary
                )

                hero.isVillain -> Triple(
                    DesignSystemR.string.tag_villain,
                    MaterialTheme.colorScheme.tertiary,
                    MaterialTheme.colorScheme.onTertiary
                )

                else -> Triple(
                    R.string.tag_neutral,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onSecondary
                )
            }

            val (tagTextRes, containerColor, contentColor) = tagInfo
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(containerColor)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = stringResource(id = tagTextRes),
                    style = MaterialTheme.typography.labelSmall,
                    color = contentColor
                )
            }

            // Bottom Name text overlay
            Text(
                text = hero.name.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 2,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}

private fun createMockHero(id: String, name: String, alignment: String): SuperHero {
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
            placeOfBirth = "Unknown",
            firstAppearance = "Unknown",
            publisher = "Vanguard Publisher",
            alignment = alignment
        ),
        appearance = Appearance(
            gender = "Male",
            race = "Human",
            height = "180 cm",
            weight = "80 kg",
            eyeColor = "Blue",
            hairColor = "Black"
        ),
        work = Work(occupation = "Hero", base = "Vanguard Base"),
        connections = Connections(groupAffiliation = "Vanguard Alliance", relatives = "None"),
        imageUrl = ""
    )
}

@Preview(name = "Hero Card - Good Alignment")
@Composable
private fun HeroCardGoodPreview() {
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                HeroCard(
                    hero = createMockHero("1", "Batman", "good"),
                    onSelected = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(name = "Hero Card - Bad Alignment")
@Composable
private fun HeroCardBadPreview() {
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                HeroCard(
                    hero = createMockHero("2", "Joker", "bad"),
                    onSelected = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(name = "Hero Card - Neutral Alignment")
@Composable
private fun HeroCardNeutralPreview() {
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                HeroCard(
                    hero = createMockHero("3", "Deadpool", "neutral"),
                    onSelected = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
