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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anenha.superhero.core.designsystem.component.SectionHeader
import com.anenha.superhero.core.designsystem.component.SegmentedPowerBar
import com.anenha.superhero.core.designsystem.component.VanguardCard
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.PowerStats
import com.anenha.superhero.features.archive.R
import com.anenha.superhero.core.designsystem.R as DesignSystemR

@Composable
fun PowerStatsSection(
    powerStats: PowerStats,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionHeader(
            text = stringResource(id = R.string.section_power_stats),
            barColor = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(16.dp))
        VanguardCard {
            StatRow(
                label = stringResource(id = DesignSystemR.string.stat_strength),
                value = powerStats.strength,
                color = getStatColor(powerStats.strength)
            )
            StatRow(
                label = stringResource(id = DesignSystemR.string.stat_intelligence),
                value = powerStats.intelligence,
                color = getStatColor(powerStats.intelligence)
            )
            StatRow(
                label = stringResource(id = DesignSystemR.string.stat_speed),
                value = powerStats.speed,
                color = getStatColor(powerStats.speed)
            )
            StatRow(
                label = stringResource(id = DesignSystemR.string.stat_durability),
                value = powerStats.durability,
                color = getStatColor(powerStats.durability)
            )
            StatRow(
                label = stringResource(id = DesignSystemR.string.stat_power),
                value = powerStats.power,
                color = getStatColor(powerStats.power)
            )
            StatRow(
                label = stringResource(id = DesignSystemR.string.stat_combat),
                value = powerStats.combat,
                color = getStatColor(powerStats.combat)
            )
        }
    }
}

@Composable
private fun getStatColor(value: Int): Color {
    return when (value) {
        in 0..33 -> MaterialTheme.colorScheme.secondary
        in 34..66 -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.tertiary
    }
}

@Composable
private fun StatRow(
    label: String,
    value: Int,
    color: Color
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$value%",
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 20.sp),
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        SegmentedPowerBar(value = value, color = color)
    }
}

@Preview(name = "Power Stats Section")
@Composable
private fun PowerStatsSectionPreview() {
    VanguardKineticTheme {
        PowerStatsSection(
            powerStats = PowerStats(
                intelligence = 92,
                strength = 60,
                speed = 55,
                durability = 85,
                power = 30,
                combat = 22
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
