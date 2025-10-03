package br.com.softmind.data.remote

import android.content.Context
import br.com.softmind.data.auth.TokenStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5094/"

    private lateinit var retrofit: Retrofit
    private lateinit var tokenStore: TokenStore

    fun init(context: Context) {
        tokenStore = TokenStore(context.applicationContext)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(DeviceIdInterceptor(context))
            .addInterceptor(AuthInterceptor(tokenStore))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 🔐 Garante que init() foi chamado antes de usar
    private fun ensureInit() {
        check(::retrofit.isInitialized) {
            "RetrofitClient.init(context) não foi chamado antes de acessar a API."
        }
    }

    val authApi: AuthApi by lazy {
        ensureInit()
        retrofit.create(AuthApi::class.java)
    }

    val api: QuestionarioApi by lazy {
        ensureInit()
        retrofit.create(QuestionarioApi::class.java)
    }

    val alertApi: AlertApi by lazy {
        ensureInit()
        retrofit.create(AlertApi::class.java)
    }

    fun tokenStore(): TokenStore {
        ensureInit()
        return tokenStore
    }
}
