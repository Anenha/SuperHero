package com.anenha.superhero.core.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme

@Composable
fun VanguardSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector = Icons.Default.Search,
    glowColor: Color = MaterialTheme.colorScheme.primary
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isFocused) glowColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
        label = "BorderColor"
    )

    val animatedGlowAlpha by animateFloatAsState(
        targetValue = if (isFocused) 0.3f else 0f,
        label = "GlowAlpha"
    )

    val animatedElevation by animateDpAsState(
        targetValue = if (isFocused) 4.dp else 0.dp,
        label = "Elevation"
    )

    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        textStyle = MaterialTheme.typography.bodyLarge,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { focusManager.clearFocus() }
        ),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .border(1.dp, animatedBorderColor, MaterialTheme.shapes.small)
            .drawWithContent {
                if (animatedGlowAlpha > 0f) {
                    val shadowColor = glowColor.copy(alpha = animatedGlowAlpha)
                    val paint = Paint().asFrameworkPaint().apply {
                        color = shadowColor.toArgb()
                        setShadowLayer(
                            animatedElevation.toPx() * 3,
                            0f, 0f,
                            shadowColor.toArgb()
                        )
                    }
                    drawContext.canvas.nativeCanvas.drawRoundRect(
                        0f, 0f, size.width, size.height,
                        8.dp.toPx(), 8.dp.toPx(),
                        paint
                    )
                }
                drawContent()
            },
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (isFocused) glowColor else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Preview
@Composable
private fun VanguardSearchBarPreview() {
    VanguardKineticTheme {
        VanguardSearchBar(
            value = "",
            onValueChange = {},
            placeholder = "Search Heroes..."
        )
    }
}
