package br.com.softmind.model

data class LoginRequest(
    val token: String // seu "token fixo" enviado no login
)

data class AuthResponse(
    val accessToken: String,
    val expiresIn: Long? = null,
    val tokenType: String? = "Bearer"
)