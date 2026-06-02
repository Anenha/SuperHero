package com.anenha.superhero.features.comparison.screen.comparison

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anenha.superhero.core.designsystem.component.VanguardCircularProgressIndicator
import com.anenha.superhero.core.designsystem.component.VanguardTopBar
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.Appearance
import com.anenha.superhero.domain.model.Biography
import com.anenha.superhero.domain.model.Connections
import com.anenha.superhero.domain.model.PowerStats
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.model.Work
import com.anenha.superhero.features.comparison.R
import com.anenha.superhero.features.comparison.component.HeadToHeadSection
import com.anenha.superhero.features.comparison.component.HexagonPortrait
import com.anenha.superhero.core.designsystem.R as DesignSystemR

@Composable
fun ComparisonScreen(
    viewModel: ComparisonViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    ComparisonScreenContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetry = viewModel::loadComparisonData,
        modifier = modifier
    )
}

@Composable
fun ComparisonScreenContent(
    uiState: ComparisonUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            VanguardTopBar(
                title = stringResource(id = R.string.comparison_title),
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
            when (uiState) {
                is ComparisonUiState.Loading -> {
                    VanguardCircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is ComparisonUiState.Success -> {
                    ComparisonSuccessContent(
                        hero1 = uiState.hero1,
                        hero2 = uiState.hero2
                    )
                }
                is ComparisonUiState.Error -> {
                    ComparisonErrorContent(
                        message = uiState.message,
                        onRetry = onRetry,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun ComparisonErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary,
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

@Composable
fun ComparisonSuccessContent(
    hero1: SuperHero,
    hero2: SuperHero,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Hexagonal Portraits & Face-off Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Hero
            HexagonPortrait(
                hero = hero1,
                borderColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            // VS Logo in between
            Text(
                text = stringResource(id = R.string.vs),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 36.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Light
                ),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            // Right Hero
            HexagonPortrait(
                hero = hero2,
                borderColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        HeadToHeadSection(
            hero1 = hero1,
            hero2 = hero2
        )
    }
}

private fun createMockHero(id: String, name: String, isVillain: Boolean): SuperHero {
    return SuperHero(
        id = id,
        name = name,
        powerstats = PowerStats(
            intelligence = if (isVillain) 85 else 80,
            strength = if (isVillain) 75 else 70,
            speed = if (isVillain) 65 else 60,
            durability = if (isVillain) 90 else 85,
            power = if (isVillain) 95 else 90,
            combat = if (isVillain) 80 else 75
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

@Preview(name = "Comparison - Loading State", showBackground = true)
@Composable
private fun ComparisonScreenContentLoadingPreview() {
    VanguardKineticTheme {
        ComparisonScreenContent(
            uiState = ComparisonUiState.Loading,
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview(name = "Comparison - Success State", showBackground = true)
@Composable
private fun ComparisonScreenContentSuccessPreview() {
    val hero1 = createMockHero("1", "Batman", false)
    val hero2 = createMockHero("2", "Joker", true)
    VanguardKineticTheme {
        ComparisonScreenContent(
            uiState = ComparisonUiState.Success(hero1, hero2),
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview(name = "Comparison - Error State", showBackground = true)
@Composable
private fun ComparisonScreenContentErrorPreview() {
    VanguardKineticTheme {
        ComparisonScreenContent(
            uiState = ComparisonUiState.Error("Failed to load comparison data due to network error."),
            onBackClick = {},
            onRetry = {}
        )
    }
}
