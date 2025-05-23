package br.com.softmind.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://621a8c81-a292-4502-88f0-657ac214560d.mock.pstmn.io/" // base do link gerado no mocky.io

    val api: QuestionarioApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionarioApi::class.java)
    }
}
