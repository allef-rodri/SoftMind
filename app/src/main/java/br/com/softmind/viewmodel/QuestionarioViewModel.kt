package br.com.softmind.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.softmind.model.CategoriaResponse
import br.com.softmind.model.Resposta
import br.com.softmind.model.RespostaRequest
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

    fun carregarQuestionario() {
        viewModelScope.launch {
            try {
                _categorias.value = repository.carregarQuestoes()
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
                val listaRespostas: List<Resposta> = respostas.map {
                    Resposta(pergunta = it.first, resposta = it.second)
                }
                repository.enviarRespostas(listaRespostas)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

