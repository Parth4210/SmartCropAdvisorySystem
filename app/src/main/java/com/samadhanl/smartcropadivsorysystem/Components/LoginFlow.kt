package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.samadhanl.smartcropadivsorysystem.MarketPriceScreen
import com.samadhanl.smartcropadivsorysystem.ViewModel.AuthViewModel
//import com.samadhanl.smartcropadivsorysystem.Components.SoilHealthScreen // <-- Add this import statement
import com.samadhanl.smartcropadivsorysystem.SoilHealthScreen

// --- Composable that handles showing Splash or Login ---
@Composable
fun LoginFlow(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val uiState = authViewModel.uiState
    Crossfade(targetState = uiState.showSplash) { isShowingSplash ->
        if (isShowingSplash) {
            // SplashScreen()
        } else {
            // --- TEMPORARY CHANGE FOR TESTING ---
            // Directly showing SoilHealthScreen and skipping login.
            //SoilHealthScreen(onNavigateBack = { }) // You need to provide any required parameters like this.
            MarketPriceScreen(onNavigateBack = { navController.popBackStack() })
            }
            /* <-- Start of comment
            // The original LoginScreen is now commented out.
            LoginScreen(
                uiState = uiState,
                onMobileChange = authViewModel::onMobileChange,
                onEmailChange = authViewModel::onEmailChange,
                onPasswordChange = authViewModel::onPasswordChange,
                onLogin = {
                    authViewModel.onLoginClicked {
                        navController.navigate("SoilHealth") {
                            popUpTo("login_flow") { inclusive = true }
                        }
                    }
                }
            )
            */ // <-- End of comment
        }
    }
