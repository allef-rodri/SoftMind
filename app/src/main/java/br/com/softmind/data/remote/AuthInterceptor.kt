package br.com.softmind.data.remote

import br.com.softmind.data.auth.TokenStore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenStore: TokenStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenStore.tokenFlow.first() }

        val reqBuilder = chain.request().newBuilder()
        if (!token.isNullOrBlank()) {
            reqBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(reqBuilder.build())
    }
}
