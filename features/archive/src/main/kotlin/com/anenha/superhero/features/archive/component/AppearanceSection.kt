package com.anenha.superhero.features.archive.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anenha.superhero.core.designsystem.component.SectionHeader
import com.anenha.superhero.core.designsystem.component.VanguardCard
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.Appearance
import com.anenha.superhero.features.archive.R

@Composable
fun AppearanceSection(
    appearance: Appearance,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionHeader(
            text = stringResource(id = R.string.section_appearance),
            barColor = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))
        VanguardCard {
            GridAttributes(
                items = listOf(
                    stringResource(id = R.string.field_gender) to appearance.gender,
                    stringResource(id = R.string.field_race) to appearance.race,
                    stringResource(id = R.string.field_height) to appearance.height,
                    stringResource(id = R.string.field_weight) to appearance.weight,
                    stringResource(id = R.string.field_eye_color) to appearance.eyeColor,
                    stringResource(id = R.string.field_hair_color) to appearance.hairColor
                )
            )
        }
    }
}

@Composable
private fun GridAttributes(items: List<Pair<String, String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (i in items.indices step 2) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Column 1
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = items[i].first,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = items[i].second.ifEmpty { stringResource(id = R.string.unknown) },
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Column 2 (if exists)
                if (i + 1 < items.size) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = items[i + 1].first,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = items[i + 1].second.ifEmpty { stringResource(id = R.string.unknown) },
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Appearance Section")
@Composable
private fun AppearanceSectionPreview() {
    VanguardKineticTheme {
        AppearanceSection(
            appearance = Appearance(
                gender = "Male",
                race = "Human",
                height = "6'2 // 188 cm",
                weight = "210 lb // 95 kg",
                eyeColor = "Blue",
                hairColor = "Black"
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
