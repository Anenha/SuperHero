package com.anenha.superhero.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.anenha.superhero.core.designsystem.R

// Dynamic Google Fonts configuration
private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val SoraFont = FontFamily(
    Font(googleFont = GoogleFont("Sora"), fontProvider = fontProvider)
)

private val HankenGroteskFont = FontFamily(
    Font(googleFont = GoogleFont("Hanken Grotesk"), fontProvider = fontProvider)
)

private val JetBrainsMonoFont = FontFamily(
    Font(googleFont = GoogleFont("JetBrains Mono"), fontProvider = fontProvider)
)

// Typography scale definitions
private val DisplayHero = TextStyle(
    fontFamily = SoraFont,
    fontSize = 72.sp,
    fontWeight = FontWeight.ExtraBold,
    lineHeight = 80.sp,
    letterSpacing = (-0.04).em
)

private val HeadlineLg = TextStyle(
    fontFamily = SoraFont,
    fontSize = 40.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 48.sp,
    letterSpacing = (-0.02).em
)

private val HeadlineLgMobile = TextStyle(
    fontFamily = SoraFont,
    fontSize = 32.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 38.sp
)

private val HeadlineMd = TextStyle(
    fontFamily = SoraFont,
    fontSize = 24.sp,
    fontWeight = FontWeight.SemiBold,
    lineHeight = 32.sp
)

private val TitleMd = TextStyle(
    fontFamily = JetBrainsMonoFont,
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 24.sp,
    letterSpacing = 0.1.em
)

private val BodyLg = TextStyle(
    fontFamily = HankenGroteskFont,
    fontSize = 18.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 28.sp
)

private val BodyMd = TextStyle(
    fontFamily = HankenGroteskFont,
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 24.sp
)

private val BodySm = TextStyle(
    fontFamily = HankenGroteskFont,
    fontSize = 14.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 20.sp
)

private val LabelLg = TextStyle(
    fontFamily = JetBrainsMonoFont,
    fontSize = 14.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 20.sp,
    letterSpacing = 0.1.em
)

private val LabelMd = TextStyle(
    fontFamily = JetBrainsMonoFont,
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 16.sp,
    letterSpacing = 0.1.em
)

private val LabelSm = TextStyle(
    fontFamily = JetBrainsMonoFont,
    fontSize = 10.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 14.sp,
    letterSpacing = 0.1.em
)

private val StatsNumber = TextStyle(
    fontFamily = SoraFont,
    fontSize = 32.sp,
    fontWeight = FontWeight.ExtraBold,
    lineHeight = 32.sp
)

val VanguardKineticTypography = Typography(
    displayLarge = DisplayHero,
    displayMedium = StatsNumber,
    displaySmall = HeadlineLgMobile,
    headlineLarge = HeadlineLg,
    headlineMedium = HeadlineMd,
    titleMedium = TitleMd,
    bodyLarge = BodyLg,
    bodyMedium = BodyMd,
    bodySmall = BodySm,
    labelLarge = LabelLg,
    labelMedium = LabelMd,
    labelSmall = LabelSm
)
