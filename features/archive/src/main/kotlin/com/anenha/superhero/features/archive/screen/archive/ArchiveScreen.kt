package com.anenha.superhero.features.archive.screen.archive

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anenha.superhero.core.designsystem.component.VanguardCircularProgressIndicator
import com.anenha.superhero.core.designsystem.component.VanguardSearchBar
import com.anenha.superhero.core.designsystem.component.VanguardTopBar
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.Appearance
import com.anenha.superhero.domain.model.Biography
import com.anenha.superhero.domain.model.Connections
import com.anenha.superhero.domain.model.PowerStats
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.model.Work
import com.anenha.superhero.features.archive.R
import com.anenha.superhero.features.archive.component.HeroCard
import com.anenha.superhero.core.designsystem.R as DesignSystemR

@Composable
fun ArchiveScreen(
    onHeroSelected: (String, String, String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val viewModel: ArchiveViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    ArchiveScreenContent(
        uiState = uiState,
        searchQuery = searchQuery,
        onSearchQueryChange = viewModel::onSearchQueryChanged,
        onRetry = viewModel::loadInitialHeroes,
        onHeroSelected = onHeroSelected,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        modifier = modifier
    )
}

@Composable
fun ArchiveScreenContent(
    uiState: ArchiveUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onRetry: () -> Unit,
    onHeroSelected: (String, String, String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            VanguardTopBar(
                title = stringResource(id = R.string.archive_title)
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Decrypted base description
            Text(
                text = stringResource(id = R.string.archive_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Extra-large search input
            VanguardSearchBar(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = stringResource(id = R.string.search_placeholder)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Content Grid
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (uiState) {
                    is ArchiveUiState.Loading -> {
                        VanguardCircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is ArchiveUiState.Success -> {
                        ArchiveSuccessContent(
                            heroes = uiState.heroes,
                            onHeroSelected = onHeroSelected,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }

                    is ArchiveUiState.Error -> {
                        ArchiveErrorContent(
                            message = uiState.message,
                            onRetry = onRetry
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchiveSuccessContent(
    heroes: List<SuperHero>,
    onHeroSelected: (String, String, String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    if (heroes.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.no_records_found),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(heroes, key = { it.id }) { hero ->
                HeroCard(
                    hero = hero,
                    onSelected = { onHeroSelected(hero.id, hero.name, hero.imageUrl) },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }
    }
}

@Composable
private fun ArchiveErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(id = DesignSystemR.string.retry),
                style = MaterialTheme.typography.labelMedium
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

@Preview(name = "Archive - Loading State")
@Composable
private fun ArchiveScreenContentLoadingPreview() {
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                ArchiveScreenContent(
                    uiState = ArchiveUiState.Loading,
                    searchQuery = "",
                    onSearchQueryChange = {},
                    onRetry = {},
                    onHeroSelected = { _, _, _ -> },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Preview(name = "Archive - Success State (Empty)")
@Composable
private fun ArchiveScreenContentEmptyPreview() {
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                ArchiveScreenContent(
                    uiState = ArchiveUiState.Success(emptyList()),
                    searchQuery = "",
                    onSearchQueryChange = {},
                    onRetry = {},
                    onHeroSelected = { _, _, _ -> },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Preview(name = "Archive - Success State (Heroes)")
@Composable
private fun ArchiveScreenContentSuccessPreview() {
    val mockHeroes = listOf(
        createMockHero("1", "Batman", "good"),
        createMockHero("2", "Joker", "bad"),
        createMockHero("3", "Deadpool", "neutral")
    )
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                ArchiveScreenContent(
                    uiState = ArchiveUiState.Success(mockHeroes),
                    searchQuery = "Search query",
                    onSearchQueryChange = {},
                    onRetry = {},
                    onHeroSelected = { _, _, _ -> },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Preview(name = "Archive - Error State")
@Composable
private fun ArchiveScreenContentErrorPreview() {
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                ArchiveScreenContent(
                    uiState = ArchiveUiState.Error("An decryption error occurred while fetching records."),
                    searchQuery = "",
                    onSearchQueryChange = {},
                    onRetry = {},
                    onHeroSelected = { _, _, _ -> },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}
