package br.com.softmind.model

data class QuestionarioResponse(
    val categorias: List<Categoria>
)

data class Categoria(
    val nome: String,
    val questoes: List<Questao>
)

data class Questao(
    val texto: String,
    val opcoes: List<String>
)
