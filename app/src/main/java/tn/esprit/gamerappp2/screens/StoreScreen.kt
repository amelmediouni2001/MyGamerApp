package tn.esprit.gamerappp2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import tn.esprit.gamerappp2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Static mock data with images (adjusted to match screenshot)
    val games = listOf(
        Triple("Red Dead Redemption 2", "$70.0", R.drawable.rdr2),
        Triple("Grand Theft Auto V", "$30.0", R.drawable.gta5),
        Triple("The Legend of Zelda", "$90.0", R.drawable.zelda),
        Triple("God of War Ragnarök", "", R.drawable.god_of_war)  // Empty price for red + button
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Store") },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Coming soon!")  // Cart click Snackbar
                        }
                    }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
                    }
                    IconButton(onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Coming soon!")  // Bell click Snackbar
                        }
                    }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE91E63),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add new */ },
                containerColor = Color(0xFFE91E63),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add , contentDescription = null)  // Cart icon for FAB
            }
        },
        bottomBar = {
            BottomNavBar(navController)  // Bottom bar
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(games) { (title, price, imageRes) ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(price, style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                if (price.isNotEmpty()) {
                                    // Cart icon for cards with price
                                    IconButton(
                                        onClick = { /* Add to cart */ }
                                    ) {
                                        Icon(
                                            Icons.Default.ShoppingCart,
                                            contentDescription = "Add to Cart",
                                            tint = Color(0xFFFFBB33)  // Yellow cart icon
                                        )
                                    }
                                } else {
                                    // Red "+" button for last card (God of War)
                                    Button(
                                        onClick = { /* Add */ },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                                    ) {
                                        Text("+", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}