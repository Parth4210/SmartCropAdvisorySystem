// In file: ForgotPassword.kt
package com.samadhanl.smartcropadivsorysystem

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samadhanl.smartcropadivsorysystem.ui.theme.SmartCropAdivsorySystemTheme

@Composable
fun ForgotPasswordScreen(
    onSendLinkClicked: () -> Unit,
    onBackToLoginClicked: () -> Unit,
    viewModel: ForgotPasswordViewModel = viewModel() // Get an instance of the ViewModel
) {
    val uiState by viewModel.uiState.collectAsState() // Observe the state from the ViewModel
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFFF7F5F0)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section
            AuthHeader(title = "Forgot Password", subtitle = "Enter your email to get a reset link")

            // Form Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // This text field is now connected to the ViewModel
                    AuthTextField(
                        value = uiState.email,
                        onValueChange = viewModel::onEmailChange,
                        label = "Email Address",
                        keyboardType = KeyboardType.Email,
                        error = uiState.emailError // Pass the error message from the ViewModel
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Buttons Section
            Button(
                onClick = {
                    // The ViewModel's validation logic is called here
                    viewModel.onSendLinkClicked {
                        // This block only runs if the email is valid
                        Toast.makeText(context, "Reset link sent!", Toast.LENGTH_SHORT).show()
                        onSendLinkClicked()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8BC34A)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Send Reset Link", fontSize = 16.sp)
            }
            TextButton(onClick = onBackToLoginClicked) {
                Text("Back to Log in", color = Color(0xFF4A7C59))
            }

            Spacer(Modifier.weight(1f)) // Pushes footer to the bottom
            AuthFooter()
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    SmartCropAdivsorySystemTheme {
        ForgotPasswordScreen(onSendLinkClicked = {}, onBackToLoginClicked = {})
    }
}