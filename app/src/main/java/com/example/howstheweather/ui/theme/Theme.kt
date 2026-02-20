package com.example.howstheweather.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SoftWhite = Color(0xFFF5F9FF)
val DreamyBlue = Color(0xFFB8D4F0)
val MediumBlue = Color(0xFF7AADD4)
val DeepBlue = Color(0xFF4A86B8)
val LightBlue = Color(0xFFDCEEFB)

val BabyBlue = Color(0xFFADD8E6)

private val DreamyColorScheme = lightColorScheme(
    primary = BabyBlue,
    onPrimary = Color.White,
    primaryContainer = DreamyBlue,
    onPrimaryContainer = Color(0xFF0D2F4A),

    secondary = MediumBlue,
    onSecondary = Color.White,
    secondaryContainer = LightBlue,
    onSecondaryContainer = Color(0xFF0D2F4A),

    background = SoftWhite,
    onBackground = Color(0xFF1A2E3F),

    surface = LightBlue,
    onSurface = Color(0xFF1A2E3F),

    surfaceVariant = DreamyBlue,
    onSurfaceVariant = Color(0xFF1A2E3F),
)

@Composable
fun HowsTheWeatherTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DreamyColorScheme,
        typography = Typography,
        content = content
    )
}