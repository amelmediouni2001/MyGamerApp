package tn.esprit.gamerappp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import tn.esprit.gamerappp2.screens.BottomNavBar
import tn.esprit.gamerappp2.screens.ForgotPasswordScreen
import tn.esprit.gamerappp2.screens.HomeScreen
import tn.esprit.gamerappp2.screens.LoginScreen
import tn.esprit.gamerappp2.screens.NewsScreen
import tn.esprit.gamerappp2.screens.OTPValidationScreen
import tn.esprit.gamerappp2.screens.ProfileScreen
import tn.esprit.gamerappp2.screens.ResetPasswordScreen
import tn.esprit.gamerappp2.screens.SignUpScreen
import tn.esprit.gamerappp2.ui.theme.GamerAppP2Theme
import tn.esprit.gamerappp2.screens.SplashScreen
import tn.esprit.gamerappp2.screens.StoreScreen
import androidx.compose.runtime.getValue

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
                        composable("home") { HomeScreen(navController) }
                        composable("login") { LoginScreen(navController) }
                        composable("signup") { SignUpScreen(navController) }
                        composable("forgotPassword") { ForgotPasswordScreen(navController) }
                        composable("OTPValidation") { OTPValidationScreen(navController, "1234") }
                        composable("reset_password") { ResetPasswordScreen(navController) }

                        // Bottom Nav Sub-Graph (FIXED: navigation DSL, no nesting conflict)
                        navigation(
                            route = "bottom_nav",
                            startDestination = "profile"
                        ) {
                            composable("news") { NewsScreen(navController) }
                            composable("store") { StoreScreen(navController) }
                            composable("profile") { ProfileScreen(navController) }
                        }
                    }
                }
            }
        }
    }

    // Bottom Nav with Bar (FIXED: Uses sub-graph navController)
    @Composable
    fun BottomNavWithBar(navController: NavHostController) {
        val navBackStackEntry by     navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            bottomBar = {
                BottomNavBar(navController)  // Your BottomNavBar (onClick uses sub-routes)
            }
        ) { innerPadding ->
            // No inner NavHost—sub-graph handles routes
            Box(modifier = Modifier.padding(innerPadding))
        }
    }
}