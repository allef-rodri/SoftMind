package br.com.softmind.repository

import android.util.Log
import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.model.WellnessResponse

class WellnessRepository {

    suspend fun loadWellnessData(): List<String> {
        Log.d("WellnessRepo", "Carregando dados da saúde")
        val response: List<WellnessResponse> = RetrofitClient.api.loadWellnessData()
        
        return response.map { it.name }

    }

}