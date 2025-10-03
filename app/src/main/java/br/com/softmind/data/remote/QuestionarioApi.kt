package br.com.softmind.data.remote

import br.com.softmind.model.CategoriaResponse
import br.com.softmind.model.Resposta
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface QuestionarioApi {

    @GET("api/CategoryQuestionnaire/GetCategoryQuestionnaire")
    suspend fun obterQuestoes(): List<CategoriaResponse>

    @POST("api/CategoryQuestionnaire/AddResponseQuestionnaire") // Ajuste o endpoint conforme sua API
    suspend fun enviarRespostas(@Body request: List<Resposta>)

    @POST("api/Mood/AddMood")
    suspend fun salvarEmojiName(@Body request: String)

}
