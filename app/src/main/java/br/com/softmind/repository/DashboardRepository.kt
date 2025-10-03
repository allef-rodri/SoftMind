package br.com.softmind.repository

import android.util.Log
import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.model.MoodDto

class DashboardRepository {

    suspend fun loadDashboardData(): List<String> {
        Log.d("DashboardRepo", "Carregando dados do dashboard")
        val dtos: List<MoodDto> = RetrofitClient.api.loadDashboardData()

        // devolver só os nomes
        return dtos.map { it.name }
    }
}