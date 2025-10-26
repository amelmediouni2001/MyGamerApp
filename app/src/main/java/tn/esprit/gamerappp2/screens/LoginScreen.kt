package com.example.gamerappp2.ui.screens  // Your current package

import android.util.Patterns  // For real email regex in validateFields
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import tn.esprit.gamerappp2.R  // Fixed: Matches your package

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun validateFields(): Boolean {
        emailError = if (email.isBlank()) "Email is required"
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email format"
        else ""

        passwordError = if (password.isBlank()) "Password is required"
        else if (password.length < 6) "Password must be at least 6 characters"
        else ""

        return emailError.isEmpty() && passwordError.isEmpty()
    }

    // Wrap in Scaffold for Snackbar support
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo (your size + padding)
            Image(
                painter = painterResource(id = R.drawable.logo_gamer),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.Fit  // Keeps proportions
            )

            // Email Field (real-time validation with isValidEmail)
            OutlinedTextField(
                value = email,
                onValueChange = { newEmail ->
                    email = newEmail
                    emailError = if (isValidEmail(newEmail)) "" else "Invalid email format"
                },
                label = { Text("Email") },
                isError = emailError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError.isNotEmpty()) {
                Text(
                    text = emailError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Password Field (real-time validation)
            OutlinedTextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                    passwordError = if (newPassword.length >= 6) "" else "Password must be 6+ chars"
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError.isNotEmpty()) {
                Text(
                    text = passwordError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Login Button (with validation)
            Button(
                onClick = {
                    if (validateFields()) {
                        navController.navigate("home")  // Add "home" route in NavHost later
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please check your inputs")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),  // Your pink/red
                    contentColor = Color.White
                )
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(8.dp))


            // Remember Me + Forgot Password (fixed Row structure) - Moved after OR for flow, but per PPT keep here? Wait, adjusted to after social if needed, but image focuses on bottom
            // Note: To match image flow (Login -> OR -> Social), I moved Remember up if needed, but kept as is; adjust if wrong
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween  // Better spacing
            ) {
                // Remember Me (Checkbox + clickable Text)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFFE91E63)  // Match button color
                        )
                    )
                    Text(
                        text = "Remember me",
                        color = Color(0xFFE91E63),  // Pink color for text
                        modifier = Modifier.clickable { rememberMe = !rememberMe }  // Toggle on click
                    )
                }

                // Forgot Password (right-aligned)
                TextButton(onClick = {
                    navController.navigate("forgot")  // Placeholder: To ForgotScreen later
                }) {
                    Text("Forgot Password?",
                        color = Color(0xFFE91E63))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // OR Divider (centered, gray text to match image)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "OR",
                    color = Color(0xFF9E9E9E),  // Gray for divider
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Social Buttons (FB + Google in Row) - With icons and colors to match image
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Facebook Button (blue background, white icon/text)
                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Coming soon - Facebook")
                        }
                    },
                    modifier = Modifier.weight(1f),  // Equal width
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1877F2),  // Facebook blue
                        contentColor = Color.White
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Facebook",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Facebook")
                    }
                }

                // Google Button (white background, black text, simple icon)
                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Coming soon - Google")
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,  // White bg
                        contentColor = Color.Black     // Black text
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Google",
                            modifier = Modifier.size(18.dp),
                            tint = Color(0xFF4285F4)  // Tint to approximate Google blue (or use custom drawable for full multi-color)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Google")
                    }
                }
            }


            // Register Button

// Fixed: "Don't have an account? Register Now" - Split text: Normal for question, pink/clickable for "Register Now"
            Spacer(modifier = Modifier.height(16.dp))  // Extra top space
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "Don't have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface  // Default/grayish color
                )
                TextButton(
                    onClick = {
                        navController.navigate("signup")  // Navigate to signup
                    }
                ) {
                    Text(
                        text = "Register Now",
                        color = Color(0xFFE91E63)  // Pink for "Register Now" only
                    )
                }
            }


        }
    }
}





// Helper function (top-level, now clearly visible)
fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()  // Upgraded to use Patterns for accuracy
}