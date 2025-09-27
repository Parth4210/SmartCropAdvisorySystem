package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.samadhanl.smartcropadivsorysystem.ForgotPasswordScreen
import com.samadhanl.smartcropadivsorysystem.RegistrationScreen
import com.samadhanl.smartcropadivsorysystem.ViewModel.AuthViewModel

@Composable
fun LoginFlow(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val uiState = authViewModel.uiState

    // Temporarily disable splash for testing
    uiState.showSplash = false

    Crossfade(targetState = false) { isShowingSplash ->
        if (isShowingSplash) {
            // SplashScreen() would go here
        } else {
            // --- The RegistrationScreen is now correctly placed inside the else block ---
            /*RegistrationScreen(
                onRegisterSuccess = { *//* Does nothing for now *//* },
                onLoginClicked = { *//* Does nothing for now *//* }
            )*/

            ForgotPasswordScreen(
                onSendLinkClicked = { /* Does nothing for now */ },
                onBackToLoginClicked = { /* Does nothing for now */ }
            )


        }
    }
}