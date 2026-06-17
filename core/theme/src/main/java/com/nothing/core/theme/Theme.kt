package com.nothing.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** CompositionLocal for selecting NDot / Inter fonts per screen. */
val LocalNothingTypography = staticCompositionLocalOf { NothingTypography() }

/** Nothing OS typography tokens. */
data class NothingTypography(
    val clockLarge: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 88.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp
    ),
    val clockCompact: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 48.sp,
        fontWeight = FontWeight.Normal
    ),
    val bodyLarge: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    val bodyMedium: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    val bodySmall: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    val labelSection: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal
    ),
    val contextMenuItem: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium
    )
)

/** Launcher theme wrapper. */
@Composable
fun NothingLauncherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = NothingTypography()
    val materialTypography = Typography(
        displayLarge = typography.clockLarge,
        headlineLarge = typography.clockCompact,
        bodyLarge = typography.bodyLarge,
        bodyMedium = typography.bodyMedium,
        bodySmall = typography.bodySmall,
        labelLarge = typography.bodySmall,
        titleMedium = typography.contextMenuItem
    )

    CompositionLocalProvider(LocalNothingTypography provides typography) {
        MaterialTheme(
            colorScheme = NothingColorScheme,
            typography = materialTypography,
            content = content
        )
    }
}
