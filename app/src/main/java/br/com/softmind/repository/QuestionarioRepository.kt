package br.com.softmind.repository

import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.model.QuestionarioResponse
import br.com.softmind.model.toQuestionarioResponse

class QuestionarioRepository {

    suspend fun carregarQuestoes(): QuestionarioResponse {
        val responseMap = RetrofitClient.api.obterQuestoes()
        return responseMap.toQuestionarioResponse()
    }
}
