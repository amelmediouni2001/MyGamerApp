package tn.esprit.gamerappp2.screens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import tn.esprit.gamerappp2.R

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var emailPhone by remember { mutableStateOf("") }
    var emailPhoneError by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Real-time validation: Not empty
    fun validateEmailPhone(input: String): String = if (input.isBlank()) "Must not be empty!" else ""

    // Full validation for buttons
    fun validateAll(): Boolean {
        emailPhoneError = validateEmailPhone(emailPhone)
        return emailPhoneError.isEmpty()
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

                // Title
                Text(
                    text = "Forgot Password email to reset your",
                    color = MaterialTheme.colorScheme.onBackground,  // Auto white/black
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Email/Phone Field (real-time)
                OutlinedTextField(
                    value = emailPhone,
                    onValueChange = { newInput ->
                        emailPhone = newInput
                        emailPhoneError = validateEmailPhone(newInput)  // Real-time error
                    },
                    label = { Text("Email/Phone", color = MaterialTheme.colorScheme.onSurface) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                    isError = emailPhoneError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,  // Red focus
                        errorIndicatorColor = MaterialTheme.colorScheme.error        // Red error
                    )
                )
                if (emailPhoneError.isNotEmpty()) {
                    Text(
                        text = emailPhoneError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (validateAll()) {
                            navController.navigate("OTPValidation")  // Nav with code 1234 (for OTP later)
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("You have some errors in your inputs!")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,  // Red
                        contentColor = MaterialTheme.colorScheme.onPrimary   // White
                    )
                ) {
                    Text("Submit", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))

                // OR
                Text(
                    text = "OR",
                    color = MaterialTheme.colorScheme.surfaceVariant,  // Auto gray
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Send SMS Button
                Button(
                    onClick = {
                        if (validateAll()) {
                            navController.navigate("OTPValidation")  // Nav with code 6789
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("You have some errors in your inputs!")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,  // Red
                        contentColor = MaterialTheme.colorScheme.onPrimary   // White
                    )
                ) {
                    Text("Send SMS", fontSize = 16.sp)
                }
            }
        }
    }
}