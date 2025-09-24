package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.samadhanl.smartcropadivsorysystem.Components.LoginScreen
import com.samadhanl.smartcropadivsorysystem.Components.SplashScreen
import com.samadhanl.smartcropadivsorysystem.ViewModel.AuthViewModel

// --- Composable that handles showing Splash or Login ---
@Composable
fun LoginFlow(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val uiState = authViewModel.uiState
    Crossfade(targetState = uiState.showSplash) { isShowingSplash ->
        if (isShowingSplash) {
            SplashScreen()
        } else {
            LoginScreen(
                uiState = uiState,
                onMobileChange = authViewModel::onMobileChange,
                onEmailChange = authViewModel::onEmailChange,
                onPasswordChange = authViewModel::onPasswordChange,
                onLogin = {
                    // When login is clicked, we call the ViewModel function
                    // and pass it the navigation action.
                    authViewModel.onLoginClicked {
                        // This is the onLoginSuccess lambda
                        navController.navigate("dashboard") {
                            // This pops the login screen from the back stack, so the user can't go back to it
                            popUpTo("login_flow") { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}