package tn.esprit.gamerappp2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import tn.esprit.gamerappp2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Static mock data for bookmarks with images
    val bookmarks = listOf(
        Triple("Red Dead Redemption 2", "$70.0", R.drawable.rdr2),
        Triple("Grand Theft Auto V", "$30.0", R.drawable.gta5)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Coming soon!")  // Bell click Snackbar
                        }
                    }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE91E63),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavBar(navController)  // Bottom bar
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
            // Profile Header Item
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar
                        Image(
                            painter = painterResource(id = R.drawable.god_of_war),  // Use your avatar image
                            contentDescription = "Profile Avatar",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(50.dp)),  // Circular
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Full Name Field (read-only style)
                        OutlinedTextField(
                            value = "John Doe",
                            onValueChange = { },
                            enabled = false,
                            label = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Email Field (read-only style)
                        OutlinedTextField(
                            value = "john@example.com",
                            onValueChange = { },
                            enabled = false,
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Edit Profile Button
                        Button(
                            onClick = { /* Edit */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                        ) {
                            Text("Edit Profile", color = Color.White)
                        }
                    }
                }
            }

            // Bookmarks Header Item
            item {
                Text(
                    text = "Bookmarks",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Bookmarks Items
            items(bookmarks) { (title, price, imageRes) ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = title,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(price, style = MaterialTheme.typography.bodyMedium)
                        }
                        IconButton(onClick = { /* Remove bookmark */ }) {
                            Icon(
                                Icons.Default.Bookmark,
                                contentDescription = "Bookmark",
                                tint = Color(0xFFE91E63)
                            )
                        }
                    }
                }
            }
        }
    }
}