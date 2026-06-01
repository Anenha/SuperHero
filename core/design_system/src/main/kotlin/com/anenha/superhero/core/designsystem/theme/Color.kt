package com.anenha.superhero.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

// Nocturnal Vanguard Kinetic Colors
private val BackgroundColor = Color(0xFF131318)
private val OnBackgroundColor = Color(0xFFE4E1E9)

private val PrimaryColor = Color(0xFFECB2FF) // Electric Purple
private val OnPrimaryColor = Color(0xFF520071)
private val PrimaryContainerColor = Color(0xFFBD00FF)
private val OnPrimaryContainerColor = Color(0xFFFFFFFF)
private val InversePrimaryColor = Color(0xFF9900CF)

private val SecondaryColor = Color(0xFFD3FBFF) // Neon Cyan
private val OnSecondaryColor = Color(0xFF00363A)
private val SecondaryContainerColor = Color(0xFF00EEFC)
private val OnSecondaryContainerColor = Color(0xFF00686F)

private val TertiaryColor = Color(0xFFFBBC00) // Amber
private val OnTertiaryColor = Color(0xFF402D00)
private val TertiaryContainerColor = Color(0xFF977000)
private val OnTertiaryContainerColor = Color(0xFFFFFFFF)

private val ErrorColor = Color(0xFFFFB4AB)
private val OnErrorColor = Color(0xFF690005)
private val ErrorContainerColor = Color(0xFF93000A)
private val OnErrorContainerColor = Color(0xFFFFDAD6)
private val SurfaceColor = Color(0xFF16161E) 
private val OnSurfaceColor = Color(0xFFE4E1E9)
private val SurfaceVariantColor = Color(0xFF35343A)
private val OnSurfaceVariantColor = Color(0xFFD4C0D7)

private val SurfaceDimColor = Color(0xFF131318)
private val SurfaceBrightColor = Color(0xFF39383E)
private val SurfaceContainerLowestColor = Color(0xFF0E0E13)
private val SurfaceContainerLowColor = Color(0xFF1B1B20)
private val SurfaceContainerColor = Color(0xFF1F1F25)
private val SurfaceContainerHighColor = Color(0xFF2A292F)
private val SurfaceContainerHighestColor = Color(0xFF35343A)

private val InverseSurfaceColor = Color(0xFFE4E1E9)
private val InverseOnSurfaceColor = Color(0xFF303036)

private val OutlineColor = Color(0xFF9D8BA0)
private val OutlineVariantColor = Color(0xFF514255)

private val SurfaceTintColor = Color(0xFFECB2FF)

// Fixed Colors
private val PrimaryFixedColor = Color(0xFFF8D8FF)
private val PrimaryFixedDimColor = Color(0xFFECB2FF)
private val OnPrimaryFixedColor = Color(0xFF320047)
private val OnPrimaryFixedVariantColor = Color(0xFF74009F)

private val SecondaryFixedColor = Color(0xFF7DF4FF)
private val SecondaryFixedDimColor = Color(0xFF00DBE9)
private val OnSecondaryFixedColor = Color(0xFF002022)
private val OnSecondaryFixedVariantColor = Color(0xFF004F54)

private val TertiaryFixedColor = Color(0xFFFFDFA0)
private val TertiaryFixedDimColor = Color(0xFFFBBC00)
private val OnTertiaryFixedColor = Color(0xFF261A00)
private val OnTertiaryFixedVariantColor = Color(0xFF5C4300)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    primaryContainer = PrimaryContainerColor,
    onPrimaryContainer = OnPrimaryContainerColor,
    inversePrimary = InversePrimaryColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    secondaryContainer = SecondaryContainerColor,
    onSecondaryContainer = OnSecondaryContainerColor,
    tertiary = TertiaryColor,
    onTertiary = OnTertiaryColor,
    tertiaryContainer = TertiaryContainerColor,
    onTertiaryContainer = OnTertiaryContainerColor,
    background = BackgroundColor,
    onBackground = OnBackgroundColor,
    surface = SurfaceColor,
    onSurface = OnSurfaceColor,
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = OnSurfaceVariantColor,
    surfaceTint = SurfaceTintColor,
    inverseSurface = InverseSurfaceColor,
    inverseOnSurface = InverseOnSurfaceColor,
    outline = OutlineColor,
    outlineVariant = OutlineVariantColor,
    error = ErrorColor,
    onError = OnErrorColor,
    errorContainer = ErrorContainerColor,
    onErrorContainer = OnErrorContainerColor,
    surfaceDim = SurfaceDimColor,
    surfaceBright = SurfaceBrightColor,
    surfaceContainer = SurfaceContainerColor,
    surfaceContainerLowest = SurfaceContainerLowestColor,
    surfaceContainerLow = SurfaceContainerLowColor,
    surfaceContainerHigh = SurfaceContainerHighColor,
    surfaceContainerHighest = SurfaceContainerHighestColor,
    primaryFixed = PrimaryFixedColor,
    onPrimaryFixed = OnPrimaryFixedColor,
    primaryFixedDim = PrimaryFixedDimColor,
    onPrimaryFixedVariant = OnPrimaryFixedVariantColor,
    secondaryFixed = SecondaryFixedColor,
    onSecondaryFixed = OnSecondaryFixedColor,
    secondaryFixedDim = SecondaryFixedDimColor,
    onSecondaryFixedVariant = OnSecondaryFixedVariantColor,
    tertiaryFixed = TertiaryFixedColor,
    onTertiaryFixed = OnTertiaryFixedColor,
    tertiaryFixedDim = TertiaryFixedDimColor,
    onTertiaryFixedVariant = OnTertiaryFixedVariantColor
)
