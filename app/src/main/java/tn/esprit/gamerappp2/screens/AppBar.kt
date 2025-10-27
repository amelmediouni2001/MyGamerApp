package tn.esprit.gamerappp2.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    showCart: Boolean = false,  // For Store only
    onNotificationClick: () -> Unit = {},  // Snackbar action
    onCartClick: () -> Unit = {}  // Cart action
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    TopAppBar(
        title = { Text(title) },
        actions = {
            if (showCart) {
                IconButton(onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Cart empty!")
                    }
                    onCartClick()
                }) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color.White  // Set ShoppingCart icon to white
                    )
                }
            }
            IconButton(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Coming soon!")
                }
                onNotificationClick()
            }) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White  // Makes icon white
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFE91E63),  // Red
            titleContentColor = Color.White
        )
    )
}