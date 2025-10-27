package tn.esprit.gamerappp2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import tn.esprit.gamerappp2.R

@Composable
fun SplashScreen(navController: NavController) {
    // Auto-navigate after 2 seconds
    LaunchedEffect(Unit) {
        delay(2000L)  // 2 seconds
        navController.navigate("news") {
            popUpTo("splash") { inclusive = true }  // Remove splash from back stack
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_gamer),
            contentDescription = "Gamer App Splash",
            modifier = Modifier.fillMaxSize(0.5f),
            contentScale = ContentScale.Fit  // Optional: Keeps logo proportions (no stretch)
        )
    }
}