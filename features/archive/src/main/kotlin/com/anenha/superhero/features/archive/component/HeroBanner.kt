package com.anenha.superhero.features.archive.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme

@Composable
fun HeroBanner(
    heroId: String,
    heroName: String,
    imageUrl: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    showName: Boolean = true
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                model = imageUrl,
                contentDescription = heroName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "hero_image_$heroId"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
        }

        // Top shadow/gradient for status bar readability
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.5f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Dark Gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
                        ),
                        startY = 400f
                    )
                )
        )

        // Name
        if (showName) {
            Text(
                text = heroName,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            )
        }
    }
}

@Preview(name = "Hero Banner Preview")
@Composable
private fun HeroBannerPreview() {
    VanguardKineticTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                HeroBanner(
                    heroId = "1",
                    heroName = "Batman",
                    imageUrl = "",
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                    modifier = Modifier.height(380.dp)
                )
            }
        }
    }
}
