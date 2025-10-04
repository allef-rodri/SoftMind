package br.com.softmind.data.remote

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5094/"

    private lateinit var retrofit: Retrofit

    fun init(context: Context) {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(DeviceIdInterceptor(context))
            .addInterceptor(AuthInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: QuestionarioApi by lazy {
        retrofit.create(QuestionarioApi::class.java)
    }

    val alertApi: AlertApi by lazy {
        retrofit.create(AlertApi::class.java)
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

}
