package br.com.softmind.model

import com.google.gson.annotations.SerializedName

data class CategoriaResponse(
    val id: Id,
    @SerializedName("name") val nome: String,
    @SerializedName("questions") val questoes: List<QuestaoResponse>
)

data class Id(
    val timestamp: Long,
    val creationTime: String
)

data class QuestaoResponse(
    @SerializedName("questionText") val texto: String,
    @SerializedName("responseOptions") val opcoes: List<String>
)

