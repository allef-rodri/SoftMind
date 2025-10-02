package br.com.softmind.model

import com.google.gson.annotations.SerializedName

data class RespostaRequest(
    val respostas: List<Resposta>
)

data class Resposta(
    @SerializedName("pergunta") val pergunta: String,
    @SerializedName("resposta") val resposta: String
)