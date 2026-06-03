package com.anenha.superhero.core.designsystem.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme

@Composable
fun VanguardCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth
    )
}

@Preview
@Composable
private fun VanguardCircularProgressIndicatorPreview() {
    VanguardKineticTheme {
        VanguardCircularProgressIndicator()
    }
}
