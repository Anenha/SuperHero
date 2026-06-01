package com.anenha.superhero.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun VanguardKineticTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = VanguardKineticTypography,
        shapes = VanguardKineticShapes,
        content = content
    )
}
