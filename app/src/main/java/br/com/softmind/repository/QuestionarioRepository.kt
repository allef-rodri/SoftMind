package br.com.softmind.repository

import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.model.CategoriaResponse
import android.util.Log
import br.com.softmind.model.Resposta

class QuestionarioRepository {

    suspend fun carregarQuestoes(): List<CategoriaResponse> {
        try {
            Log.d("QuestionarioRepo", "Iniciando chamada à API para obter questionários...")
            val categorias = RetrofitClient.api.obterQuestoes()
            Log.d("QuestionarioRepo", "Chamada à API concluída. Categorias recebidas: ${categorias.size}")
            return categorias
        } catch (e: Exception) {
            Log.e("QuestionarioRepo", "Exceção ao carregar questionários: ${e.message}", e)
            throw e
        }
    }

    suspend fun enviarRespostas(respostas: List<Resposta>) {
        Log.d("QuestionarioRepo", "Enviando respostas: $respostas")
        RetrofitClient.api.enviarRespostas(respostas)
    }
}

