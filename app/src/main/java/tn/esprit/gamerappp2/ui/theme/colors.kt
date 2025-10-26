package tn.esprit.gamerappp2.ui.theme  // Your theme package

import androidx.compose.ui.graphics.Color

object GamerColors {  // "Object" = singleton, like a global toolbox
    // Core colors for the app
    val PrimaryRed = Color(0xf90043)  // Bright red for buttons/header
    val DarkRed = Color(0xFFE50914)     // Optional: Darker variant for dark mode

    // Social buttons
    val FacebookBlue = Color(0xFF1877F2)
    val GoogleYellow = Color(0xFFFBB03B)

    // Neutrals (for text, backgrounds)
    val OnPrimary = Color.White        // White text on red
    val Error = Color(0xFFB00020)      // Reddish for errors
    val SurfaceVariant = Color(0xFFE0E0E0)  // Gray for dividers/Snackbars

    // Light/Dark mode support (easy extension)
    val isDarkMode = false  // Toggle this or read from system later
    val Background = if (isDarkMode) Color.Black else Color.White
    val OnBackground = if (isDarkMode) Color.White else Color.Black
}