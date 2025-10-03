package br.com.softmind.repository

import android.util.Log
import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.model.WellnessResponse

class WellnessRepository {

    suspend fun loadWellnessData(): List<String> {
        Log.d("WellnessRepo", "Carregando dados da saúde")
        return try {
            val response: List<WellnessResponse> = RetrofitClient.api.loadWellnessData()
            response.map { it.name }
        } catch (e: Exception) {
            Log.e("WellnessRepo", "Erro ao carregar dados da saúde: ${e.message}", e)
            throw e
        }

    }

}