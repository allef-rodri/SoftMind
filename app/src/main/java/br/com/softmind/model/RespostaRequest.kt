package br.com.softmind.model

data class RespostaRequest(
    val respostas: List<Resposta>
)

data class Resposta(
    val pergunta: String,
    val resposta: String
)
