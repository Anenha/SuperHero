package com.anenha.superhero.features.archive.screen.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anenha.superhero.core.designsystem.component.VanguardCircularProgressIndicator
import com.anenha.superhero.core.designsystem.component.VanguardLargeTopBar
import com.anenha.superhero.core.designsystem.component.VanguardTopBar
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.Appearance
import com.anenha.superhero.domain.model.Biography
import com.anenha.superhero.domain.model.Connections
import com.anenha.superhero.domain.model.PowerStats
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.model.Work
import com.anenha.superhero.features.archive.R
import com.anenha.superhero.features.archive.component.AffiliationsSection
import com.anenha.superhero.features.archive.component.AppearanceSection
import com.anenha.superhero.features.archive.component.BiographySection
import com.anenha.superhero.features.archive.component.CompareSection
import com.anenha.superhero.features.archive.component.HeroBanner
import com.anenha.superhero.features.archive.component.PowerStatsSection
import com.anenha.superhero.features.archive.component.RelativesSection
import com.anenha.superhero.features.archive.component.WorkSection
import com.anenha.superhero.core.designsystem.R as DesignSystemR

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onBackClick: () -> Unit,
    onCompareClick: (String, String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val compareQuery by viewModel.compareQuery.collectAsState()
    val dropdownResults by viewModel.dropdownResults.collectAsState()
    val isDropdownLoading by viewModel.isDropdownLoading.collectAsState()

    DetailsScreenContent(
        uiState = uiState,
        compareQuery = compareQuery,
        dropdownResults = dropdownResults,
        isDropdownLoading = isDropdownLoading,
        onBackClick = onBackClick,
        onCompareQueryChange = viewModel::onCompareQueryChanged,
        onCompareClick = { targetId ->
            val state = uiState
            if (state is DetailsUiState.Success) {
                onCompareClick(state.hero.id, targetId)
            }
        },
        onRetry = viewModel::loadHeroDetails,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        modifier = modifier
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenContent(
    uiState: DetailsUiState,
    compareQuery: String,
    dropdownResults: List<SuperHero>,
    isDropdownLoading: Boolean,
    onBackClick: () -> Unit,
    onCompareQueryChange: (String) -> Unit,
    onCompareClick: (String) -> Unit,
    onRetry: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    when (val state = uiState) {
        is DetailsUiState.Success -> {
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
            Scaffold(
                topBar = {
                    VanguardLargeTopBar(
                        title = state.hero.name,
                        onBackClick = onBackClick,
                        scrollBehavior = scrollBehavior,
                        expandedHeight = 380.dp,
                        windowInsets = WindowInsets.statusBars,
                        background = {
                            HeroBanner(
                                heroId = state.hero.id,
                                heroName = state.hero.name,
                                imageUrl = state.hero.imageUrl,
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedVisibilityScope,
                                showName = false,
                                modifier = Modifier
                                    .matchParentSize()
                                    .graphicsLayer {
                                        alpha = 1f - scrollBehavior.state.collapsedFraction
                                    }
                            )
                        }
                    )
                },
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                modifier = modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) { innerPadding ->
                DetailsSuccessContent(
                    hero = state.hero,
                    compareQuery = compareQuery,
                    dropdownResults = dropdownResults,
                    isDropdownLoading = isDropdownLoading,
                    onCompareQueryChange = onCompareQueryChange,
                    onCompareClick = onCompareClick,
                    modifier = Modifier
                        .padding(innerPadding)
                        .navigationBarsPadding()
                )
            }
        }
        else -> {
            Scaffold(
                topBar = {
                    VanguardTopBar(
                        title = stringResource(
                            id = when (uiState) {
                                is DetailsUiState.Error -> R.string.details_title // Or some default
                                else -> R.string.details_title
                            }
                        ),
                        onBackClick = onBackClick
                    )
                },
                modifier = modifier.fillMaxSize()
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (uiState is DetailsUiState.Loading) {
                        VanguardCircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else if (uiState is DetailsUiState.Error) {
                        DetailsErrorContent(
                            message = uiState.message,
                            onRetry = onRetry
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun DetailsSuccessContent(
    hero: SuperHero,
    compareQuery: String,
    dropdownResults: List<SuperHero>,
    isDropdownLoading: Boolean,
    onCompareQueryChange: (String) -> Unit,
    onCompareClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
            // Scrollable attributes
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                PowerStatsSection(powerstats = hero.powerstats)

                Spacer(modifier = Modifier.height(32.dp))

                CompareSection(
                    currentHeroId = hero.id,
                    compareQuery = compareQuery,
                    dropdownResults = dropdownResults,
                    isDropdownLoading = isDropdownLoading,
                    onCompareQueryChange = onCompareQueryChange,
                    onCompareClick = onCompareClick
                )

                Spacer(modifier = Modifier.height(32.dp))

                AppearanceSection(appearance = hero.appearance)

                Spacer(modifier = Modifier.height(32.dp))

                BiographySection(biography = hero.biography)

                Spacer(modifier = Modifier.height(32.dp))

                WorkSection(work = hero.work)

                Spacer(modifier = Modifier.height(32.dp))

                AffiliationsSection(groupAffiliation = hero.connections.groupAffiliation)

                Spacer(modifier = Modifier.height(32.dp))

                RelativesSection(relatives = hero.connections.relatives)

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
}

@Composable
private fun DetailsErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.tertiary
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
            placeOfBirth = "Gotham",
            firstAppearance = "Unknown",
            publisher = "Vanguard Publisher",
            alignment = alignment
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

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview(name = "Details - Loading State")
@Composable
private fun DetailsScreenContentLoadingPreview() {
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                DetailsScreenContent(
                    uiState = DetailsUiState.Loading,
                    compareQuery = "",
                    dropdownResults = emptyList(),
                    isDropdownLoading = false,
                    onBackClick = {},
                    onCompareQueryChange = {},
                    onCompareClick = {},
                    onRetry = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview(name = "Details - Success State")
@Composable
private fun DetailsScreenContentSuccessPreview() {
    val mockHero = createMockHero("1", "Batman", "good")
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                DetailsScreenContent(
                    uiState = DetailsUiState.Success(mockHero),
                    compareQuery = "",
                    dropdownResults = emptyList(),
                    isDropdownLoading = false,
                    onBackClick = {},
                    onCompareQueryChange = {},
                    onCompareClick = {},
                    onRetry = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview(name = "Details - Error State")
@Composable
private fun DetailsScreenContentErrorPreview() {
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                DetailsScreenContent(
                    uiState = DetailsUiState.Error("An decryption error occurred while fetching details."),
                    compareQuery = "",
                    dropdownResults = emptyList(),
                    isDropdownLoading = false,
                    onBackClick = {},
                    onCompareQueryChange = {},
                    onCompareClick = {},
                    onRetry = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}
