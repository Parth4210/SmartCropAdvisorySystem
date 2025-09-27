package com.samadhanl.smartcropadivsorysystem.Data

data class AuthUiState(
    var showSplash: Boolean = true,
    val mobile: String = "",
    val email: String = "",
    val password: String = "",
    val error: String? = null
)