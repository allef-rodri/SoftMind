package br.com.softmind.repository

import br.com.softmind.data.auth.TokenStore
import br.com.softmind.data.remote.AuthApi
import br.com.softmind.model.LoginRequest

class AuthRepository(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore
) {
    suspend fun loginWithFixedToken(fixedToken: String) {
        val resp = authApi.login(LoginRequest(token = fixedToken))
        require(!resp.accessToken.isNotBlank()) { "Token JWT não retornado pela API." }
        tokenStore.saveToken(resp.accessToken)
    }

    suspend fun logout() {
        tokenStore.clearToken()
    }
}
