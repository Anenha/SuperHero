package com.anenha.superhero.features.archive.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.anenha.superhero.features.archive.R

@Composable
fun RelativesSection(
    relatives: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionHeader(
            text = stringResource(id = R.string.section_relatives),
            barColor = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        VanguardCard {
            Text(
                text = relatives.ifEmpty { stringResource(id = R.string.unknown) },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(name = "Relatives Section")
@Composable
private fun RelativesSectionPreview() {
    VanguardKineticTheme {
        RelativesSection(
            relatives = "Damian Wayne (son), Dick Grayson (adopted son), Tim Drake (adopted son), Jason Todd (adopted son), Cassandra Cain (adopted daughter)",
            modifier = Modifier.padding(16.dp)
        )
    }
}
