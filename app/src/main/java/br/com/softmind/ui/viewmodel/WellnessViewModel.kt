package br.com.softmind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.softmind.repository.WellnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estados possíveis da tela de Wellness
 */
sealed class WellnessUiState {
    object Loading : WellnessUiState()
    data class Success(val orientations: List<String>) : WellnessUiState()
    data class Error(val message: String) : WellnessUiState()
}

/**
 * ViewModel para a tela de Wellness
 */
class WellnessViewModel(
    private val repository: WellnessRepository = WellnessRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<WellnessUiState>(WellnessUiState.Loading)
    val uiState: StateFlow<WellnessUiState> = _uiState.asStateFlow()

    init {
        loadWellnessData()
    }

    /**
     * Carrega os dados de bem-estar da API
     */
    fun loadWellnessData() {
        viewModelScope.launch {
            _uiState.value = WellnessUiState.Loading

            try {
                val orientations = repository.loadWellnessData()

                if (orientations.isEmpty()) {
                    _uiState.value = WellnessUiState.Error("Nenhuma orientação disponível no momento.")
                } else {
                    _uiState.value = WellnessUiState.Success(orientations)
                }
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("Unable to resolve host") == true ->
                        "Sem conexão com a internet. Verifique sua conexão."
                    e.message?.contains("timeout") == true ->
                        "Tempo esgotado. Tente novamente."
                    else ->
                        "Erro ao carregar orientações: ${e.message ?: "Erro desconhecido"}"
                }
                _uiState.value = WellnessUiState.Error(errorMessage)
            }
        }
    }

    /**
     * Função para retry em caso de erro
     */
    fun retry() {
        loadWellnessData()
    }
}