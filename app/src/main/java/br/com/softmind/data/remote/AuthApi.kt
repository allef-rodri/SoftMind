package br.com.softmind.data.remote

import br.com.softmind.model.AuthResponse
import br.com.softmind.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequest): AuthResponse
}
