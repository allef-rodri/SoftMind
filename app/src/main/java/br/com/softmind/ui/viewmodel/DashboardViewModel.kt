package br.com.softmind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.softmind.repository.DashboardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Representa o estado de UI para a tela de dashboard.
 * Utilizamos um sealed class para que a tela possa reagir de forma declarativa
 * aos diferentes estados: carregando, sucesso ou erro.
 */
sealed class DashboardUiState {
    /**
     * Indica que os dados do dashboard estão sendo carregados.
     */
    object Loading : DashboardUiState()

    /**
     * Indica que os dados foram carregados com sucesso.
     * @param data lista de moods ou nomes retornados pela API
     */
    data class Success(val data: List<String>) : DashboardUiState()

    /**
     * Indica que ocorreu algum erro ao carregar os dados.
     * @param message mensagem que pode ser exibida na interface para informar o usuário
     */
    data class Error(val message: String) : DashboardUiState()
}

/**
 * ViewModel responsável por gerenciar os dados do dashboard.
 * Agora expõe um StateFlow de DashboardUiState para que a UI possa
 * apresentar estados de carregamento, sucesso ou erro de forma consistente.
 */
class DashboardViewModel(
    private val repository: DashboardRepository = DashboardRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    /**
     * Fluxo imutável que a interface observa para reagir a mudanças de estado.
     */
    val uiState: StateFlow<DashboardUiState> = _uiState

    /**
     * Mantém compatibilidade com telas que apenas observam a lista de moods. Para estados
     * de erro ou carregamento, será emitida uma lista vazia. Quando o estado é sucesso,
     * emite-se a lista de dados retornados. Isso permite que telas legadas continuem
     * funcionando sem modificações imediatas.
     */
    val dashboardData: StateFlow<List<String>> =
        _uiState
            .map { state ->
                when (state) {
                    is DashboardUiState.Success -> state.data
                    else -> emptyList()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )

    init {
        loadDashboardData()
    }

    /**
     * Inicia o carregamento dos dados do dashboard. Atualiza o estado para Loading,
     * realiza a chamada na repository e, dependendo do resultado, emite Success ou Error.
     */
    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            try {
                val lista: List<String> = repository.loadDashboardData()
                _uiState.value = DashboardUiState.Success(lista)
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("Unable to resolve host") == true ->
                        "Você está offline ou há problemas com a rede. Por favor, verifique a conexão e tente novamente."
                    e.message?.contains("timeout") == true ->
                        "A solicitação demorou demais. Tente novamente."
                    else ->
                        "Não foi possível carregar os dados do dashboard. Por favor, tente novamente mais tarde."
                }
                _uiState.value = DashboardUiState.Error(errorMessage)
            }
        }
    }

    /**
     * Função utilitária que permite ao usuário tentar carregar os dados novamente em caso de erro.
     */
    fun retry() {
        loadDashboardData()
    }
}