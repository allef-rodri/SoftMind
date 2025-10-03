package br.com.softmind.repository

import android.util.Log
import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.model.MoodDto

class DashboardRepository {

    suspend fun loadDashboardData(): List<String> {
        Log.d("DashboardRepo", "Carregando dados do dashboard")
        return try {
            val dtos: List<MoodDto> = RetrofitClient.api.loadDashboardData()
            // devolver só os nomes
            dtos.map { it.name }
        } catch (e: Exception) {
            // registra o erro e propaga para quem chamou lidar com ele
            Log.e("DashboardRepo", "Erro ao carregar dados do dashboard: ${e.message}", e)
            throw e
        }
    }
}