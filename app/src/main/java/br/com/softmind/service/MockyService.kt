package br.com.softmind.service

import br.com.softmind.model.Mocky
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MockyService {
    //https://run.mocky.io/v3/d1db33ab-3522-4d71-afd4-09c4d271dae9
    @GET("{idService}")
    fun getDataMocky(@Path("idService") idService: String): Call<Mocky>
}