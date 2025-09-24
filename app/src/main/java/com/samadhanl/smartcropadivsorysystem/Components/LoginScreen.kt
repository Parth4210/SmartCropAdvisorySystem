package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samadhanl.smartcropadivsorysystem.Data.AuthUiState

// ===================================================================
//               LOGIN AND SPLASH SCREEN CODE
// ===================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: AuthUiState,
    onMobileChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFf7f3e3)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- Header ---
        Text(text = "🌾", fontSize = 32.sp)
        Text("Farmer's Login", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6b4f1d), modifier = Modifier.padding(top = 8.dp))
        Text("Welcome to Smart Crop Advisory", fontSize = 16.sp, color = Color(0xFF8d7752), modifier = Modifier.padding(top = 4.dp, bottom = 20.dp))

        // --- Form Card ---
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFfffbe6)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Mobile Number", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(value = uiState.mobile, onValueChange = onMobileChange, placeholder = { Text("Enter mobile number") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true)
                Spacer(Modifier.height(16.dp))

                Text("Email Address", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(value = uiState.email, onValueChange = onEmailChange, placeholder = { Text("Enter email address") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true)
                Spacer(Modifier.height(16.dp))

                Text("Password", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(value = uiState.password, onValueChange = onPasswordChange, placeholder = { Text("Enter password") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), singleLine = true)
                Spacer(Modifier.height(16.dp))

                if (uiState.error != null) {
                    Text(text = uiState.error, color = Color(0xFFd32f2f), fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
                }

                Button(onClick = onLogin, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7bb661)), elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)) {
                    Text("Log in", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(10.dp))
                ClickableText(text = AnnotatedString("Forgot Password?"), onClick = {}, style = TextStyle(color = Color(0xFF4e944f), fontSize = 15.sp, textAlign = TextAlign.Center, textDecoration = TextDecoration.Underline), modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(10.dp))
                ClickableText(text = AnnotatedString("New user? Register"), onClick = {}, style = TextStyle(color = Color(0xFF4e944f), fontSize = 15.sp, textAlign = TextAlign.Center, textDecoration = TextDecoration.Underline), modifier = Modifier.fillMaxWidth())
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text("🌱 Empowering Farmers • Growing Together", color = Color(0xFF8d7752), fontSize = 14.sp, fontStyle = FontStyle.Italic, modifier = Modifier.padding(bottom = 24.dp))
    }
}
