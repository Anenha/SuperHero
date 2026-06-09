package com.anenha.superhero.features.comparison.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anenha.superhero.core.designsystem.component.BidirectionalPowerBar
import com.anenha.superhero.core.designsystem.component.SectionHeader
import com.anenha.superhero.core.designsystem.component.VanguardCard
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.domain.model.Appearance
import com.anenha.superhero.domain.model.Biography
import com.anenha.superhero.domain.model.Connections
import com.anenha.superhero.domain.model.PowerStats
import com.anenha.superhero.domain.model.SuperHero
import com.anenha.superhero.domain.model.Work
import com.anenha.superhero.features.comparison.R
import com.anenha.superhero.core.designsystem.R as DesignSystemR

@Composable
fun HeadToHeadSection(
    hero1: SuperHero,
    hero2: SuperHero,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SectionHeader(
            text = stringResource(id = R.string.section_head_to_head),
            barColor = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        VanguardCard {
            CompareStatRow(
                label = stringResource(id = DesignSystemR.string.stat_strength),
                leftVal = hero1.powerstats.strength,
                rightVal = hero2.powerstats.strength
            )
            CompareStatRow(
                label = stringResource(id = DesignSystemR.string.stat_intelligence),
                leftVal = hero1.powerstats.intelligence,
                rightVal = hero2.powerstats.intelligence
            )
            CompareStatRow(
                label = stringResource(id = DesignSystemR.string.stat_speed),
                leftVal = hero1.powerstats.speed,
                rightVal = hero2.powerstats.speed
            )
            CompareStatRow(
                label = stringResource(id = DesignSystemR.string.stat_durability),
                leftVal = hero1.powerstats.durability,
                rightVal = hero2.powerstats.durability
            )
            CompareStatRow(
                label = stringResource(id = DesignSystemR.string.stat_power),
                leftVal = hero1.powerstats.power,
                rightVal = hero2.powerstats.power
            )
            CompareStatRow(
                label = stringResource(id = DesignSystemR.string.stat_combat),
                leftVal = hero1.powerstats.combat,
                rightVal = hero2.powerstats.combat
            )
        }
    }

}

@Composable
private fun CompareStatRow(
    label: String,
    leftVal: Int,
    rightVal: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left stat value
            Text(
                text = leftVal.toString(),
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 24.sp),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )

            // Stat Name
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center
            )

            // Right stat value
            Text(
                text = rightVal.toString(),
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 24.sp),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        BidirectionalPowerBar(
            leftValue = leftVal,
            rightValue = rightVal,
            leftColor = MaterialTheme.colorScheme.primary,
            rightColor = MaterialTheme.colorScheme.secondary
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

@Preview(name = "Head to Head Section Preview")
@Composable
private fun HeadToHeadSectionPreview() {
    val hero1 = createMockHero("1", "Batman", false)
    val hero2 = createMockHero("2", "Joker", true)
    VanguardKineticTheme {
        HeadToHeadSection(
            hero1 = hero1,
            hero2 = hero2,
            modifier = Modifier.padding(16.dp)
        )
    }
}
