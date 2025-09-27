// In file: RegistrationViewModel.kt
package com.samadhanl.smartcropadivsorysystem

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.util.Patterns



// This data class holds the current state of the registration form
data class RegistrationUiState(
    val fullName: String = "",
    val mobileNumber: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    // Error messages
    val fullNameError: String? = null,
    val mobileNumberError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)

class RegistrationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()

    fun onFullNameChange(name: String) {
        _uiState.update { it.copy(fullName = name, fullNameError = null) }
    }

    fun onMobileChange(mobile: String) {
        _uiState.update { it.copy(mobileNumber = mobile, mobileNumberError = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null) }
    }

    fun onRegisterClicked(onSuccess: () -> Unit) {
        // Reset all errors before validating again
        _uiState.update { it.copy(fullNameError = null, mobileNumberError = null, emailError = null, passwordError = null, confirmPasswordError = null) }

        val currentState = _uiState.value
        var hasError = false

        // --- Validation Rules ---
        if (currentState.fullName.isBlank()) {
            _uiState.update { it.copy(fullNameError = "Full name cannot be empty") }
            hasError = true
        }

        if (currentState.mobileNumber.length != 10) {
            _uiState.update { it.copy(mobileNumberError = "Mobile number must be 10 digits") }
            hasError = true
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            _uiState.update { it.copy(emailError = "Please enter a valid email address") }
            hasError = true
        }

        if (currentState.password.length < 8) {
            _uiState.update { it.copy(passwordError = "Password must be at least 8 characters") }
            hasError = true
        }

        if (currentState.password != currentState.confirmPassword) {
            _uiState.update { it.copy(confirmPasswordError = "Passwords do not match") }
            hasError = true
        }

        // If there are no errors, proceed with registration
        if (!hasError) {
            // In a real app, you would send this data to your server.
            // For now, we just call the onSuccess lambda.
            onSuccess()
        }
    }
}