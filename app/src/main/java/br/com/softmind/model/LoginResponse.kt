package br.com.softmind.model

data class LoginResponse(
    val token: String,
    val androidId: String,
    val expiresAt: String,
    val message: String
)
