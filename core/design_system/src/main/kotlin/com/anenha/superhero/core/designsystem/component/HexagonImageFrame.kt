package com.anenha.superhero.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme

val HexagonShape = GenericShape { size, _ ->
    val width = size.width
    val height = size.height
    val quarterWidth = width * 0.25f
    val threeQuarterWidth = width * 0.75f
    val halfHeight = height * 0.5f

    moveTo(quarterWidth, 0f)
    lineTo(threeQuarterWidth, 0f)
    lineTo(width, halfHeight)
    lineTo(threeQuarterWidth, height)
    lineTo(quarterWidth, height)
    lineTo(0f, halfHeight)
    close()
}

@Composable
fun HexagonImageFrame(
    borderColor: Color,
    modifier: Modifier = Modifier,
    borderWidth: Dp = 2.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(HexagonShape)
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .drawWithContent {
                drawContent()
                val path = Path().apply {
                    val width = size.width
                    val height = size.height
                    val quarterWidth = width * 0.25f
                    val threeQuarterWidth = width * 0.75f
                    val halfHeight = height * 0.5f

                    moveTo(quarterWidth, 0f)
                    lineTo(threeQuarterWidth, 0f)
                    lineTo(width, halfHeight)
                    lineTo(threeQuarterWidth, height)
                    lineTo(quarterWidth, height)
                    lineTo(0f, halfHeight)
                    close()
                }
                drawPath(
                    path = path,
                    color = borderColor,
                    style = Stroke(width = borderWidth.toPx())
                )
            }
    ) {
        content()
    }
}

@Preview
@Composable
private fun HexagonImageFramePreview() {
    VanguardKineticTheme {
        HexagonImageFrame(
            borderColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp)
        ) {}
    }
}
