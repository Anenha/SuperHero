package com.anenha.superhero.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme

@Composable
fun BidirectionalPowerBar(
    leftValue: Int, // 0 to 100
    rightValue: Int, // 0 to 100
    modifier: Modifier = Modifier,
    leftColor: Color = MaterialTheme.colorScheme.primary,
    rightColor: Color = MaterialTheme.colorScheme.secondary,
) {
    val leftFilled = (leftValue / 20).coerceIn(0, 5)
    val rightFilled = (rightValue / 20).coerceIn(0, 5)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End)
        ) {
            for (i in 5 downTo 1) {
                val isFilled = i <= leftFilled
                val segmentColor = if (isFilled) leftColor else leftColor.copy(alpha = 0.2f)
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(8.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(segmentColor)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)
        ) {
            for (i in 1..5) {
                val isFilled = i <= rightFilled
                val segmentColor = if (isFilled) rightColor else rightColor.copy(alpha = 0.2f)
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(8.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(segmentColor)
                )
            }
        }
    }
}

@Preview
@Composable
private fun BidirectionalPowerBarPreview() {
    VanguardKineticTheme {
        BidirectionalPowerBar(leftValue = 60, rightValue = 80)
    }
}
