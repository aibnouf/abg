package com.medical.abginsights.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Brand colors based on ABG Insights design
private val CalmBlue = Color(0xFF64B5F6)
private val MutedGreen = Color(0xFF81C784)
private val LightGrey = Color(0xFFF0F4F8)
private val DarkGrey = Color(0xFF37474F)
private val ErrorRed = Color(0xFFEF5350)

private val LightColorScheme = lightColorScheme(
    primary = CalmBlue,
    secondary = MutedGreen,
    tertiary = Color(0xFF90CAF9),
    background = LightGrey,
    surface = Color.White,
    error = ErrorRed,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = DarkGrey,
    onSurface = DarkGrey,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = CalmBlue,
    secondary = MutedGreen,
    tertiary = Color(0xFF90CAF9),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    error = ErrorRed,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
    onError = Color.White
)

@Composable
fun ABGInsightsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
