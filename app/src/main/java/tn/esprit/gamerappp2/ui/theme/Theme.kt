package tn.esprit.gamerappp2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFE91E63),      // Your pink/red for buttons/links
    onPrimary = Color.White,          // White text on red
    background = Color.Black,         // Black bg for dark mode
    onBackground = Color.White,       // White text on black
    surface = Color(0xFF121212),      // Dark gray for surfaces (fields, cards)
    onSurface = Color.White,          // White on surfaces
    error = Color(0xFFE91E63),        // Red for errors
    surfaceVariant = Color(0xFF424242) // Dark gray for dividers/Snackbars
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE91E63),      // Same red for consistency
    onPrimary = Color.White,
    background = Color.White,         // White bg for light
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFE91E63),
    surfaceVariant = Color(0xFFE0E0E0) // Light gray
)

@Composable
fun GamerAppP2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),  // Auto-detect
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}