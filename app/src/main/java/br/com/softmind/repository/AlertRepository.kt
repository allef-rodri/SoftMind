package br.com.softmind.repository

import android.util.Log
import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.model.AlertResponse
import br.com.softmind.model.MarkAsReadRequest

class AlertRepository {

    suspend fun getRecentAlerts(): List<AlertResponse> {
        return try {
            RetrofitClient.alertApi.getRecentAlerts()
        } catch (e: IllegalStateException) {
            Log.w("AlertRepo", "Nenhum alerta recente: resposta não é um array, retornando lista vazia")
            emptyList()
        } catch (e: Exception) {
            Log.e("AlertRepo", "Erro ao carregar alertas recentes: ${e.message}", e)
            throw e
        }
    }

    suspend fun markAsRead(alertId: String) {
        try {
            RetrofitClient.alertApi.markAsRead(MarkAsReadRequest(alertId))
        } catch (e: Exception) {
            Log.e("AlertRepo", "Erro ao marcar alerta como lido: ${e.message}", e)
            throw e
        }
    }

    suspend fun getRandomAlert(): AlertResponse {
        return try {
            RetrofitClient.alertApi.getRandomAlert()
        } catch (e: Exception) {
            Log.e("AlertRepo", "Erro ao obter alerta aleatório: ${e.message}", e)
            throw e
        }
    }
}
