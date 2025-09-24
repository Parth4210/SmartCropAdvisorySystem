package com.samadhanl.smartcropadivsorysystem.Data

data class AuthUiState(
    val showSplash: Boolean = true,
    val mobile: String = "",
    val email: String = "",
    val password: String = "",
    val error: String? = null
)