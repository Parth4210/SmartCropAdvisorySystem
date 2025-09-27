// In file: ForgotPasswordViewModel.kt
package com.samadhanl.smartcropadivsorysystem

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.util.Patterns

// Holds the current state of the forgot password form
data class ForgotPasswordUiState(
    val email: String = "",
    val emailError: String? = null
)

class ForgotPasswordViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        // Update the email value and clear any previous error
        _uiState.update { it.copy(email = email, emailError = null) }
    }

    fun onSendLinkClicked(onSuccess: () -> Unit) {
        val email = _uiState.value.email

        // --- Validation Rule ---
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(emailError = "Please enter a valid email address") }
            return // Stop execution if email is invalid
        }

        // If validation is successful, clear any errors and call the success lambda
        _uiState.update { it.copy(emailError = null) }
        onSuccess()
    }
}