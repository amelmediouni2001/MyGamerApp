package tn.esprit.gamerappp2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import tn.esprit.gamerappp2.R
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import tn.esprit.gamerappp2.ui.theme.GamerAppP2Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf(0) } // 0: News, 1: Store, 2: Profile (fixed to News selected)

    // Static mock data with images (adjusted titles/descriptions to match screenshot)
    val newsItems = listOf(
        Triple("TOP 10 GAMING NEWS OF THE DAY", "Here's the latest news from the gaming and e-sports world.", R.drawable.top_10),
        Triple("The Legend of Zelda Tears of the Kingdom", "The Legend of Zelda", R.drawable.zelda_game)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News") },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Coming soon!")  // Cart click Snackbar
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
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFE91E63),
                contentColor = Color.White
            ) {
                val items = listOf(
                    "News" to Icons.Default.Newspaper,
                    "Store" to Icons.Default.ShoppingCart,
                    "Profile" to Icons.Default.Person
                )
                items.forEachIndexed { index, pair ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            when (index) {
                                0 -> { /* already on News */ }
                                1 -> navController.navigate("store")
                                2 -> navController.navigate("profile")
                            }
                        },
                        icon = { Icon(pair.second, contentDescription = pair.first) },
                        label = { Text(pair.first)
                        }
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(newsItems) { (title, desc, imageRes) ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                title,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                desc,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Justify
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { /* Show more */ }) {
                                Text("Show More")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    GamerAppP2Theme {
        tn.esprit.gamerappp2.screens.NewsScreen(rememberNavController())
    }
}