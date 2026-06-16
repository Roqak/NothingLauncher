package com.nothing.core.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

/** Material 3 color scheme tuned to Nothing OS monochrome palette. */
internal val NothingColorScheme = darkColorScheme(
    primary = NothingColors.OnBackground,
    onPrimary = NothingColors.Background,
    primaryContainer = NothingColors.Surface,
    onPrimaryContainer = NothingColors.OnBackground,
    secondary = NothingColors.OnBackgroundMuted,
    onSecondary = NothingColors.OnBackground,
    background = NothingColors.Background,
    onBackground = NothingColors.OnBackground,
    surface = NothingColors.Surface,
    onSurface = NothingColors.OnBackground,
    surfaceVariant = NothingColors.Surface,
    onSurfaceVariant = NothingColors.OnBackgroundMuted,
    outline = NothingColors.Divider,
    scrim = NothingColors.Scrim
)
