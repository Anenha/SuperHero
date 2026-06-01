package com.anenha.superhero.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anenha.superhero.core.designsystem.theme.PrimaryColor
import com.anenha.superhero.core.designsystem.theme.SecondaryColor
import com.anenha.superhero.core.designsystem.theme.SuperheroTheme

@Composable
fun BidirectionalPowerBar(
    leftValue: Int, // 0 to 100
    rightValue: Int, // 0 to 100
    modifier: Modifier = Modifier,
    leftColor: Color = PrimaryColor,
    rightColor: Color = SecondaryColor,
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
                        .clip(RoundedCornerShape(2.dp))
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
                        .clip(RoundedCornerShape(2.dp))
                        .background(segmentColor)
                )
            }
        }
    }
}

@Preview
@Composable
private fun BidirectionalPowerBarPreview() {
    SuperheroTheme {
        BidirectionalPowerBar(leftValue = 60, rightValue = 80)
    }
}
