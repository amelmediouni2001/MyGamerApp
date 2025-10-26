package tn.esprit.gamerappp2.screens  // Your package

import android.util.Patterns
import androidx.compose.foundation.Image  // ← ADDED: For logo Image
import androidx.compose.foundation.background  // ← ADDED: For Box bg
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email  // ← ADDED: Email icon
import androidx.compose.material.icons.filled.Face  // ← ADDED: FB icon
import androidx.compose.material.icons.filled.Lock  // ← ADDED: Lock icon
import androidx.compose.material.icons.filled.Visibility  // ← ADDED: Eye on
import androidx.compose.material.icons.filled.VisibilityOff  // ← ADDED: Eye off
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import tn.esprit.gamerappp2.R  // Your R import

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }  // For eye toggle
    var rememberMe by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun validateFields(): Boolean {
        emailError = if (email.isBlank()) "Must not be empty!"
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email format"
        else ""

        passwordError = if (password.isBlank()) "Must not be empty!"
        else if (password.length < 6) "Password must be at least 6 characters"
        else ""

        return emailError.isEmpty() && passwordError.isEmpty()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.error,  // Red bg
                    contentColor = MaterialTheme.colorScheme.onError   // White text
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)  // Auto black/white
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo_gamer),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 32.dp),
                    contentScale = ContentScale.Fit
                )

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { newEmail ->
                        email = newEmail
                        emailError = if (newEmail.isBlank()) "Must not be empty!" else if (isValidEmail(newEmail)) "" else "Invalid email format"
                    },
                    label = { Text("Email", color = MaterialTheme.colorScheme.onSurface) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                    isError = emailError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,  // Red focus
                        errorIndicatorColor = MaterialTheme.colorScheme.error        // Red error
                    )
                )
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Password Field with eye toggle
                OutlinedTextField(
                    value = password,
                    onValueChange = { newPassword ->
                        password = newPassword
                        passwordError = if (newPassword.isBlank()) "Must not be empty!" else if (newPassword.length >= 6) "" else "Password must be 6+ chars"
                    },
                    label = { Text("Password", color = MaterialTheme.colorScheme.onSurface) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = passwordError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        errorIndicatorColor = MaterialTheme.colorScheme.error
                    )
                )
                if (passwordError.isNotEmpty()) {
                    Text(
                        text = passwordError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                Button(
                    onClick = {
                        if (validateFields()) {
                            navController.navigate("home")
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("You have some errors in your inputs!")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,  // Auto red
                        contentColor = MaterialTheme.colorScheme.onPrimary   // Auto white
                    )
                ) {
                    Text("Login")
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Remember + Forgot
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary  // Auto red
                            )
                        )
                        Text(
                            text = "Remember me",
                            color = MaterialTheme.colorScheme.primary,  // Auto red
                            modifier = Modifier.clickable { rememberMe = !rememberMe }
                        )
                    }
                    TextButton(onClick = { navController.navigate("forgot") }) {
                        Text("Forgot Password?", color = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // OR
                Text(
                    text = "OR",
                    color = MaterialTheme.colorScheme.surfaceVariant,  // Auto gray
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Social Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // FB
                    Button(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Coming soon")
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1877F2),  // FB blue (hardcoded for brand)
                            contentColor = Color.White
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Facebook")
                        }
                    }
                    // Google
                    Button(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Coming soon")
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,  // White (adapts tint)
                            contentColor = Color.Black
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF4285F4))
                            Spacer(Modifier.width(8.dp))
                            Text("Google")
                        }
                    }
                }
            }

            // Register at bottom
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = MaterialTheme.colorScheme.onBackground  // Auto white/black
                )
                TextButton(onClick = { navController.navigate("signup") }) {
                    Text("Register Now", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}



fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}