package com.anenha.superhero.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.anenha.superhero.core.designsystem.R

// Nocturnal Vanguard Kinetic Colors
val BackgroundColor = Color(0xFF131318)
val SurfaceColor = Color(0xFF16161E)
val SurfaceBrightColor = Color(0xFF39383E)
val SurfaceContainerLowestColor = Color(0xFF0E0E13)
val SurfaceContainerColor = Color(0xFF1F1F25)
val SurfaceContainerHighColor = Color(0xFF2A292F)

val PrimaryColor = Color(0xFFECB2FF) // Electric Purple
val OnPrimaryColor = Color(0xFF520071)
val SecondaryColor = Color(0xFFD3FBFF) // Neon Cyan
val OnSecondaryColor = Color(0xFF00363A)
val TertiaryColor = Color(0xFFFBBC00) // Amber (used for warnings/legendary and high contrast error)
val OnTertiaryColor = Color(0xFF402D00)

val OnSurfaceColor = Color(0xFFE4E1E9)
val OnSurfaceVariantColor = Color(0xFFD4C0D7)
val OutlineColor = Color(0xFF9D8BA0)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    tertiary = TertiaryColor,
    onTertiary = OnTertiaryColor,
    background = BackgroundColor,
    onBackground = OnSurfaceColor,
    surface = SurfaceColor,
    onSurface = OnSurfaceColor,
    onSurfaceVariant = OnSurfaceVariantColor,
    outline = OutlineColor,
    surfaceVariant = SurfaceContainerColor
)

// Dynamic Google Fonts configuration
@OptIn(ExperimentalTextApi::class)
val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val SoraFont = FontFamily(
    Font(googleFont = GoogleFont("Sora"), fontProvider = fontProvider)
)

val HankenGroteskFont = FontFamily(
    Font(googleFont = GoogleFont("Hanken Grotesk"), fontProvider = fontProvider)
)

val JetBrainsMonoFont = FontFamily(
    Font(googleFont = GoogleFont("JetBrains Mono"), fontProvider = fontProvider)
)

// Typography scale definitions
val DisplayHero = TextStyle(
    fontFamily = SoraFont,
    fontSize = 72.sp,
    fontWeight = FontWeight.ExtraBold,
    lineHeight = 80.sp,
    letterSpacing = (-0.04).sp
)

val HeadlineLg = TextStyle(
    fontFamily = SoraFont,
    fontSize = 40.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 48.sp,
    letterSpacing = (-0.02).sp
)

val HeadlineLgMobile = TextStyle(
    fontFamily = SoraFont,
    fontSize = 32.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 38.sp
)

val HeadlineMd = TextStyle(
    fontFamily = SoraFont,
    fontSize = 24.sp,
    fontWeight = FontWeight.SemiBold,
    lineHeight = 32.sp
)

val BodyLg = TextStyle(
    fontFamily = HankenGroteskFont,
    fontSize = 18.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 28.sp
)

val BodyMd = TextStyle(
    fontFamily = HankenGroteskFont,
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 24.sp
)

val LabelCaps = TextStyle(
    fontFamily = JetBrainsMonoFont,
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 16.sp,
    letterSpacing = 0.1.sp
)

val StatsNumber = TextStyle(
    fontFamily = SoraFont,
    fontSize = 32.sp,
    fontWeight = FontWeight.ExtraBold,
    lineHeight = 32.sp
)

@Composable
fun SuperheroTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
