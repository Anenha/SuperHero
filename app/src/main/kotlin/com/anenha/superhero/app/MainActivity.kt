package com.anenha.superhero.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEvent
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.NavigationEventInput
import androidx.navigationevent.setViewTreeNavigationEventDispatcherOwner
import com.anenha.superhero.core.designsystem.theme.VanguardKineticTheme
import com.anenha.superhero.features.archive.screen.archive.ArchiveScreen
import com.anenha.superhero.features.archive.screen.details.DetailsScreen
import com.anenha.superhero.features.comparison.screen.comparison.ComparisonScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), NavigationEventDispatcherOwner {
    private lateinit var backCallback: OnBackPressedCallback

    private val navigationEventInput = object : NavigationEventInput() {
        override fun onHasEnabledHandlersChanged(hasEnabledHandlers: Boolean) {
            if (::backCallback.isInitialized) {
                backCallback.isEnabled = hasEnabledHandlers
            }
        }

        fun triggerBack() {
            dispatchOnBackStarted(NavigationEvent(NavigationEvent.EDGE_NONE))
            dispatchOnBackCompleted()
        }
    }

    override val navigationEventDispatcher: NavigationEventDispatcher = NavigationEventDispatcher(
        onBackCompletedFallback = {
            backCallback.isEnabled = false
            onBackPressedDispatcher.onBackPressed()
            backCallback.isEnabled = navigationEventDispatcher.isEnabled
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        backCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                navigationEventInput.triggerBack()
            }
        }
        window.decorView.setViewTreeNavigationEventDispatcherOwner(this)
        navigationEventDispatcher.addInput(navigationEventInput)
        onBackPressedDispatcher.addCallback(this, backCallback)

        setContent {
            VanguardKineticTheme {
                AppNavigation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationEventDispatcher.dispose()
    }
}

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(ArchiveRoute)

    SharedTransitionLayout {
        NavDisplay(
            backStack = backStack,
            onBack = {
                if (backStack.size > 1) {
                    backStack.removeAt(backStack.size - 1)
                }
            },
            entryProvider = { key ->
                when (key) {
                    is ArchiveRoute -> NavEntry(key) {
                        ArchiveScreen(
                            onHeroSelected = { id, name, imageUrl ->
                                backStack.add(DetailsRoute(id = id, name = name, imageUrl = imageUrl))
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = LocalNavAnimatedContentScope.current
                        )
                    }
                    is DetailsRoute -> NavEntry(key) {
                        DetailsScreen(
                            heroId = key.id,
                            heroName = key.name,
                            heroImageUrl = key.imageUrl,
                            onBackClick = {
                                backStack.removeAt(backStack.size - 1)
                            },
                            onCompareClick = { id1, id2 ->
                                backStack.add(ComparisonRoute(id1, id2))
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = LocalNavAnimatedContentScope.current
                        )
                    }
                    is ComparisonRoute -> NavEntry(key) {
                        ComparisonScreen(
                            hero1Id = key.id1,
                            hero2Id = key.id2,
                            onBackClick = {
                                backStack.removeAt(backStack.size - 1)
                            }
                        )
                    }
                    else -> NavEntry(key) {
                        Text("Unknown Destination")
                    }
                }
            }
        )
    }
}
