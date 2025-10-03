package br.com.softmind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.softmind.model.AlertResponse
import br.com.softmind.repository.AlertRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AlertsUiState {
    object Loading : AlertsUiState()
    data class Success(val alerts: List<AlertResponse>) : AlertsUiState()
    data class Error(val message: String) : AlertsUiState()
}

class AlertsViewModel(
    private val repository: AlertRepository = AlertRepository()
) : ViewModel() {

    private var hasGeneratedInSession: Boolean
        get() = persistedHasGeneratedInSession
        set(value) { persistedHasGeneratedInSession = value }

    companion object {
        private var persistedHasGeneratedInSession: Boolean = false
    }

    private val _uiState = MutableStateFlow<AlertsUiState>(AlertsUiState.Loading)
    val uiState: StateFlow<AlertsUiState> = _uiState.asStateFlow()

    init {
        loadAlerts()
    }

    fun loadAlerts() {
        viewModelScope.launch {
            _uiState.value = AlertsUiState.Loading
            try {
                var alerts = repository.getRecentAlerts().filter { !it.isRead }

                if (alerts.size < 3 && !hasGeneratedInSession) {
                    val missing = 3 - alerts.size
                    repeat(missing) {
                        try {
                            repository.getRandomAlert()
                        } catch (_: Exception) {
                            return@repeat
                        }
                    }
                    hasGeneratedInSession = true
                    alerts = repository.getRecentAlerts().filter { !it.isRead }
                }

                _uiState.value = AlertsUiState.Success(alerts)
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("Unable to resolve host") == true ->
                        "Você está offline ou há problemas com a rede. Por favor, verifique a conexão e tente novamente."
                    e.message?.contains("timeout") == true ->
                        "A solicitação demorou demais. Tente novamente."
                    else ->
                        "Não foi possível carregar os alertas. Por favor, tente novamente mais tarde."
                }
                _uiState.value = AlertsUiState.Error(errorMessage)
            }
        }
    }

    fun markAsRead(alertId: String) {
        viewModelScope.launch {
            try {
                repository.markAsRead(alertId)
                val currentState = _uiState.value
                if (currentState is AlertsUiState.Success) {
                    val updated = currentState.alerts.map { alert ->
                        if (alert.id == alertId) alert.copy(isRead = true) else alert
                    }
                    val filtered = updated.filter { !it.isRead }
                    _uiState.value = AlertsUiState.Success(filtered)
                }
            } catch (_: Exception) {
            }
        }
    }

    fun refresh() = loadAlerts()
}
