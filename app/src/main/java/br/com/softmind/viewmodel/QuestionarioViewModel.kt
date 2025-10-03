package br.com.softmind.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.softmind.model.CategoriaResponse
import br.com.softmind.model.Resposta
import br.com.softmind.repository.QuestionarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestionarioViewModel : ViewModel() {

    private val repository = QuestionarioRepository()

    private val _categorias = MutableStateFlow<List<CategoriaResponse>?>(null)
    val categorias: StateFlow<List<CategoriaResponse>?> = _categorias

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Fluxo para expor mensagens de erro para a UI
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun carregarQuestionario() {
        viewModelScope.launch {
            try {
                _categorias.value = repository.carregarQuestoes()
            } catch (e: Exception) {
                // Mapeia a mensagem de erro para algo mais amigável
                val friendlyMessage = when {
                    e.message?.contains("Unable to resolve host") == true ->
                        "Você está offline ou há problemas com a rede. Verifique sua conexão e tente novamente."
                    e.message?.contains("timeout") == true ->
                        "A solicitação demorou demais. Tente novamente."
                    else ->
                        "Não foi possível carregar o questionário. Por favor, tente novamente mais tarde."
                }
                _errorMessage.value = friendlyMessage
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun enviarRespostas(respostas: List<Pair<String, String>>) {
        viewModelScope.launch {
            try {
                val listaRespostas: List<Resposta> = respostas.map {
                    Resposta(pergunta = it.first, resposta = it.second)
                }
                repository.enviarRespostas(listaRespostas)
            } catch (e: Exception) {
                // Atualiza mensagem de erro para que a UI possa reagir de forma amigável
                val friendlyMessage = when {
                    e.message?.contains("Unable to resolve host") == true ->
                        "Você está offline ou há problemas com a rede. Verifique sua conexão e tente novamente."
                    e.message?.contains("timeout") == true ->
                        "A solicitação demorou demais. Tente novamente."
                    else ->
                        "Não foi possível enviar as respostas. Por favor, tente novamente mais tarde."
                }
                _errorMessage.value = friendlyMessage
            }
        }
    }

    /**
     * Permite que a interface tente novamente carregar o questionário após um erro.
     */
    fun retryCarregarQuestionario() {
        _isLoading.value = true
        _errorMessage.value = null
        carregarQuestionario()
    }

    /**
     * Limpa a mensagem de erro após ser exibida.
     */
    fun clearError() {
        _errorMessage.value = null
    }

}

