package br.com.softmind.data.remote

import retrofit2.http.GET

interface QuestionarioApi {
    @GET("teste1") // somente o final do link do mocky.io
    suspend fun obterQuestoes(): Map<String, Map<String, List<Any>>>
}
