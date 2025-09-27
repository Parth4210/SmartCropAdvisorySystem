// In file: Registration.kt
package com.samadhanl.smartcropadivsorysystem

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // <-- Important import
import com.samadhanl.smartcropadivsorysystem.ui.theme.SmartCropAdivsorySystemTheme

@Composable
fun RegistrationScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClicked: () -> Unit,
    viewModel: RegistrationViewModel = viewModel() // Get an instance of the ViewModel
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
            AuthHeader(title = "Farmer's Registration", subtitle = "Create your account")

            // Form Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Each text field is connected to the ViewModel's state and error state
                    AuthTextField(value = uiState.fullName, onValueChange = viewModel::onFullNameChange, label = "Full Name", error = uiState.fullNameError)
                    Spacer(Modifier.height(16.dp))
                    AuthTextField(value = uiState.mobileNumber, onValueChange = viewModel::onMobileChange, label = "Mobile Number", keyboardType = KeyboardType.Phone, error = uiState.mobileNumberError)
                    Spacer(Modifier.height(16.dp))
                    AuthTextField(value = uiState.email, onValueChange = viewModel::onEmailChange, label = "Email Address", keyboardType = KeyboardType.Email, error = uiState.emailError)
                    Spacer(Modifier.height(16.dp))
                    AuthTextField(value = uiState.password, onValueChange = viewModel::onPasswordChange, label = "Password", isPassword = true, error = uiState.passwordError)
                    Spacer(Modifier.height(16.dp))
                    AuthTextField(value = uiState.confirmPassword, onValueChange = viewModel::onConfirmPasswordChange, label = "Confirm Password", isPassword = true, error = uiState.confirmPasswordError)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Buttons Section
            Button(
                onClick = {
                    viewModel.onRegisterClicked {
                        // This block will only run if validation is successful
                        Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
                        onRegisterSuccess()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8BC34A)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Register", fontSize = 16.sp)
            }
            TextButton(onClick = onLoginClicked) {
                Text("Already have an account? Log in", color = Color(0xFF4A7C59))
            }

            Spacer(Modifier.weight(1f))
            AuthFooter()
            Spacer(Modifier.height(16.dp))
        }
    }
}

/**
 * Reusable text field, now with error handling.
 */
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?, // New parameter to accept an error message
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        Text(label, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Enter ${label.lowercase()}") },
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            // This will highlight the field in red if there is an error
            isError = error != null
        )
        // This will show the error message in red text below the field
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}


/**
 * Reusable header for auth screens.
 */
@Composable
fun AuthHeader(title: String, subtitle: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 40.dp)
    ) {
        // Make sure you have 'ic_app_logo.xml' in res/drawable
        Image(
            painter = painterResource(id = R.drawable.leaf),
            contentDescription = "App Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF38761D))
        Spacer(Modifier.height(8.dp))
        Text(subtitle, fontSize = 16.sp, color = Color.Gray)
    }
}

/**
 * Reusable footer for auth screens.
 */
@Composable
fun AuthFooter() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Make sure you have 'ic_leaf_icon.xml' in res/drawable
        Icon(
            painter = painterResource(id = R.drawable.leaf),
            contentDescription = "Leaf icon",
            tint = Color(0xFF4A7C59),
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            "Empowering Farmers • Growing Together",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    SmartCropAdivsorySystemTheme {
        RegistrationScreen(onRegisterSuccess = {}, onLoginClicked = {})
    }
}