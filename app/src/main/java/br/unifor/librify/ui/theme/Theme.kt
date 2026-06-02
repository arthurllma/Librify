package br.unifor.librify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BlueHighlight,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
)

private val LightColorScheme = lightColorScheme(
    primary = BlueHighlight,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = LightBackground,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
)

@Composable
fun LibrifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
