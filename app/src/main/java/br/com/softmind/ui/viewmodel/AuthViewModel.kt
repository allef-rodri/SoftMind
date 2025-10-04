package br.com.softmind.ui.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.database.util.AuthManager
import br.com.softmind.database.util.Constants
import br.com.softmind.model.LoginRequest

class AuthViewModel : ViewModel() {
    private val authApi = RetrofitClient.authApi

    suspend fun login(): Boolean {
        return try {
            val response = authApi.login(LoginRequest(Constants.USERNAME, Constants.PASSWORD))
            AuthManager.token = response.token
            AuthManager.expiresAt = response.expiresAt
            Log.d("AUTH", "Token recebido: ${response.token}")
            true
        } catch (e: Exception) {
            Log.e("AUTH", "Erro no login", e) // captura stacktrace completo
            false
        }
    }
}