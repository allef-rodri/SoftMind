package br.com.softmind.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.softmind.model.QuestionarioResponse
import br.com.softmind.model.Resposta
import br.com.softmind.model.RespostaRequest
import br.com.softmind.repository.QuestionarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestionarioViewModel : ViewModel() {

    private val repository = QuestionarioRepository()

    private val _questionario = MutableStateFlow<QuestionarioResponse?>(null)
    val questionario: StateFlow<QuestionarioResponse?> = _questionario

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun carregarQuestionario() {
        viewModelScope.launch {
            try {
                _questionario.value = repository.carregarQuestoes()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun enviarRespostas(respostas: List<Pair<String, String>>) {
        viewModelScope.launch {
            try {
                val request = RespostaRequest(
                    respostas = respostas.map { Resposta(it.first, it.second) }
                )
                repository.enviarRespostas(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
