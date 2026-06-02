package com.anenha.superhero.features.archive.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anenha.superhero.core.designsystem.component.SectionHeader
import com.anenha.superhero.core.designsystem.component.VanguardCard
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.Work
import com.anenha.superhero.features.archive.R

@Composable
fun WorkSection(
    work: Work,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionHeader(
            text = stringResource(id = R.string.section_work),
            barColor = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(16.dp))
        VanguardCard {
            KeyValueColumn(
                items = listOf(
                    stringResource(id = R.string.field_occupation) to work.occupation,
                    stringResource(id = R.string.field_base) to work.base
                )
            )
        }
    }
}

@Composable
private fun KeyValueColumn(items: List<Pair<String, String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items.forEach { (key, value) ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = key,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value.ifEmpty { stringResource(id = R.string.unknown) },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(name = "Work Section", showBackground = true)
@Composable
private fun WorkSectionPreview() {
    VanguardKineticTheme {
        WorkSection(
            work = Work(
                occupation = "Businessman, Philanthropist, Vigilante",
                base = "Batcave, Wayne Manor, Gotham City; Wayne Enterprises"
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
