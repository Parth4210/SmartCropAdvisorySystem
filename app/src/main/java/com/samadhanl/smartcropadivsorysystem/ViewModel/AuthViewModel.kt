package com.samadhanl.smartcropadivsorysystem.ViewModel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samadhanl.smartcropadivsorysystem.Data.AuthUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// --- Data and ViewModel for Login ---
class AuthViewModel : ViewModel() {
    var uiState by mutableStateOf(AuthUiState())
        private set

    init {
        viewModelScope.launch {
            delay(2500)
            uiState = uiState.copy(showSplash = false)
        }
    }

    fun onMobileChange(newValue: String) {
        if (newValue.all { it.isDigit() } && newValue.length <= 10) {
            uiState = uiState.copy(mobile = newValue)
        }
    }

    fun onEmailChange(newValue: String) {
        uiState = uiState.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    // *** IMPORTANT CHANGE HERE ***
    // This function now triggers the navigation on success
    fun onLoginClicked(onLoginSuccess: () -> Unit) {
        uiState = uiState.copy(error = null)

        if (uiState.mobile.length != 10) {
            uiState = uiState.copy(error = "Mobile number must be 10 digits.")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches()) {
            uiState = uiState.copy(error = "Please enter a valid email address.")
            return
        }
        if (uiState.password.length < 6) {
            uiState = uiState.copy(error = "Password must be at least 6 characters.")
            return
        }

        // --- Handle Login Logic Here ---
        // If validation passes, call the success callback to navigate
        onLoginSuccess()
    }
}