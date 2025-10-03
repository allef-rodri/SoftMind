package br.com.softmind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.softmind.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel() : ViewModel() {
    private val _dashboardData = MutableStateFlow<List<String>>(emptyList())
    val dashboardData: StateFlow<List<String>> = _dashboardData

    private val repository = DashboardRepository()

    init { loadDashboardData() }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                val lista: List<String> = repository.loadDashboardData()
                _dashboardData.value = lista
            } catch (e: Exception) {
                println("Erro ao carregar dados do dashboard: ${e.message}")
            }
        }
    }
}