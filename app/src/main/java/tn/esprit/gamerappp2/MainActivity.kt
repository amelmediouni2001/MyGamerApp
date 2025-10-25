package tn.esprit.gamerappp2

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tn.esprit.gamerappp2.screens.SplashScreen
import tn.esprit.gamerappp2.ui.theme.GamerAppP2Theme  // Your theme import

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GamerAppP2Theme {  // Wrap in your app's theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Navigation setup
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "splash"  // Start with splash
                    ) {
                        composable("splash") { SplashScreen(navController) }
//                        composable("login") { LoginScreen(navController) }
                        // We'll add more screens later (e.g., "signup")
                    }
                }
            }
        }
    }
}