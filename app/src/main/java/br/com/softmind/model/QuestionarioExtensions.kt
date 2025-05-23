package br.com.softmind.model

fun Map<String, Map<String, List<Any>>>.toQuestionarioResponse(): QuestionarioResponse {
    val categorias = this.map { (categoriaNome, questoesMap) ->
        val questoes = questoesMap.map { (pergunta, opcoes) ->
            val opcoesString = opcoes.map { it.toString() }
            Questao(texto = pergunta, opcoes = opcoesString)
        }
        Categoria(nome = categoriaNome, questoes = questoes)
    }
    return QuestionarioResponse(categorias)
}
