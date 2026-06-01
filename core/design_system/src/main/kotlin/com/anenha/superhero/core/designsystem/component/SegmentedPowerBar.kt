package com.anenha.superhero.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anenha.superhero.core.designsystem.theme.PrimaryColor
import com.anenha.superhero.core.designsystem.theme.SuperheroTheme

@Composable
fun SegmentedPowerBar(
    value: Int, // 0 to 100
    color: Color,
    modifier: Modifier = Modifier
) {
    val filledSegments = (value / 10).coerceIn(0, 10)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 0 until 10) {
            val isFilled = i < filledSegments
            val segmentColor = if (isFilled) color else color.copy(alpha = 0.2f)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(segmentColor)
            )
        }
    }
}

@Preview
@Composable
private fun SegmentedPowerBarPreview() {
    SuperheroTheme {
        SegmentedPowerBar(value = 75, color = PrimaryColor)
    }
}
