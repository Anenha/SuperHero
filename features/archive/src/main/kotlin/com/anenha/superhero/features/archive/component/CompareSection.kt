package com.anenha.superhero.features.archive.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anenha.superhero.core.designsystem.component.SectionHeader
import com.anenha.superhero.core.designsystem.component.VanguardButton
import com.anenha.superhero.core.designsystem.component.VanguardCard
import com.anenha.superhero.core.designsystem.component.VanguardCircularProgressIndicator
import com.anenha.superhero.core.designsystem.component.VanguardSearchBar
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.Appearance
import com.anenha.superhero.domain.model.Biography
import com.anenha.superhero.domain.model.Connections
import com.anenha.superhero.domain.model.PowerStats
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.model.Work
import com.anenha.superhero.features.archive.R
import com.anenha.superhero.core.designsystem.R as designSystemR

@Composable
fun CompareSection(
    currentHeroId: String,
    compareQuery: String,
    dropdownResults: List<SuperHero>,
    isDropdownLoading: Boolean,
    onCompareQueryChange: (String) -> Unit,
    onCompareClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedComparedHero by remember { mutableStateOf<SuperHero?>(null) }
    var showDropdown by remember { mutableStateOf(false) }

    LaunchedEffect(compareQuery) {
        if (compareQuery.isEmpty()) {
            selectedComparedHero = null
        }
    }

    Column(modifier = modifier) {
        SectionHeader(
            text = stringResource(id = R.string.section_compare),
            barColor = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        VanguardCard {
            Text(
                text = stringResource(id = R.string.compare_with_label),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                VanguardSearchBar(
                    value = compareQuery,
                    onValueChange = {
                        onCompareQueryChange(it)
                        showDropdown = true
                    },
                    placeholder = stringResource(id = R.string.compare_search_placeholder)
                )

                // Dropdown popup
                if (showDropdown && (dropdownResults.isNotEmpty() || isDropdownLoading)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.outline,
                                MaterialTheme.shapes.small
                            )
                            .heightIn(max = 200.dp)
                    ) {
                        if (isDropdownLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                VanguardCircularProgressIndicator(
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            dropdownResults.forEach { targetHero ->
                                if (targetHero.id != currentHeroId) {
                                    DropdownItem(
                                        hero = targetHero,
                                        onClick = {
                                            selectedComparedHero = targetHero
                                            onCompareQueryChange(targetHero.name)
                                            showDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            VanguardButton(
                isEnable = selectedComparedHero != null,
                text = stringResource(id = R.string.compare_button_text),
                onClick = {
                    selectedComparedHero?.let { target ->
                        onCompareClick(target.id)
                    }
                },
                icon = ImageVector.vectorResource(id = designSystemR.drawable.ic_compare_arrows)
            )
        }
    }
}

@Composable
private fun DropdownItem(
    hero: SuperHero,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = hero.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (hero.biography.fullName.isNotEmpty()) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "(${hero.biography.fullName})",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun createMockHero(id: String, name: String): SuperHero {
    return SuperHero(
        id = id,
        name = name,
        powerstats = PowerStats(50, 50, 50, 50, 50, 50),
        biography = Biography(name, "", emptyList(), "", "", "", "good"),
        appearance = Appearance("", "", "", "", "", ""),
        work = Work("", ""),
        connections = Connections("", ""),
        imageUrl = ""
    )
}

@Preview(name = "Compare Section - Idle")
@Composable
private fun CompareSectionIdlePreview() {
    VanguardKineticTheme {
        CompareSection(
            currentHeroId = "1",
            compareQuery = "",
            dropdownResults = emptyList(),
            isDropdownLoading = false,
            onCompareQueryChange = {},
            onCompareClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Compare Section - Loading Search Results")
@Composable
private fun CompareSectionLoadingPreview() {
    VanguardKineticTheme {
        CompareSection(
            currentHeroId = "1",
            compareQuery = "Bat",
            dropdownResults = emptyList(),
            isDropdownLoading = true,
            onCompareQueryChange = {},
            onCompareClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Compare Section - With Results")
@Composable
private fun CompareSectionResultsPreview() {
    val results = listOf(
        createMockHero("2", "Batman"),
        createMockHero("3", "Batgirl")
    )
    VanguardKineticTheme {
        CompareSection(
            currentHeroId = "1",
            compareQuery = "Bat",
            dropdownResults = results,
            isDropdownLoading = false,
            onCompareQueryChange = {},
            onCompareClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
