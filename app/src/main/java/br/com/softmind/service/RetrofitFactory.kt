package br.com.softmind.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val URL = "https://run.mocky.io/v3/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getDataMockyService(): MockyService {
        return retrofitFactory.create(MockyService::class.java)
    }
}