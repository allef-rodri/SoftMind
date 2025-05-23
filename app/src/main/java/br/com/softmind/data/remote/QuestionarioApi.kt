package br.com.softmind.data.remote

import br.com.softmind.model.RespostaRequest
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface QuestionarioApi {

    @GET("avaliacao/perguntas") // somente o final do link do mocky.io
    suspend fun obterQuestoes(): Map<String, Map<String, List<Any>>>

    @POST("avaliacao/respostas") // Ajuste o endpoint conforme sua API
    suspend fun enviarRespostas(@Body request: RespostaRequest)

}