@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.anenha.superhero.core.designsystem.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.anenha.superhero.core.designsystem.R
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VanguardTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(id = R.string.back),
                        tint = contentColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = contentColor,
            navigationIconContentColor = contentColor
        ),
        windowInsets = windowInsets
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VanguardLargeTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    containerColor: Color = Color.Transparent,
    contentColor: Color = Color.White,
    titleContentColor: Color = Color.White,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    expandedHeight: Dp = TopAppBarDefaults.LargeAppBarExpandedHeight,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    titleAlpha: Float = 1f,
    background: @Composable BoxScope.() -> Unit = {}
) {
    Box {
        background()
        LargeTopAppBar(
            title = {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = titleContentColor.copy(alpha = titleAlpha),
                    modifier = modifier
                )
            },
            navigationIcon = {
                if (onBackClick != null) {
                    val iconModifier = if (animatedVisibilityScope != null) {
                        with(animatedVisibilityScope) {
                            Modifier.animateEnterExit(
                                enter = fadeIn(),
                                exit = fadeOut()
                            )
                        }
                    } else {
                        Modifier
                    }

                    Box(modifier = iconModifier) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back),
                                tint = contentColor
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = containerColor,
                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = titleContentColor,
                navigationIconContentColor = contentColor
            ),
            scrollBehavior = scrollBehavior,
            windowInsets = windowInsets,
            expandedHeight = expandedHeight,
            modifier = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                with(sharedTransitionScope) {
                    Modifier.renderInSharedTransitionScopeOverlay()
                }
            } else {
                Modifier
            }
        )
    }
}

@Preview
@Composable
private fun VanguardTopBarPreview() {
    VanguardKineticTheme {
        VanguardTopBar(title = "ARCHIVE", onBackClick = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun VanguardLargeTopBarPreview() {
    VanguardKineticTheme {
        VanguardLargeTopBar(
            title = "BATMAN",
            onBackClick = {},
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}
