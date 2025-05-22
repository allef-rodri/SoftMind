// Crie um novo arquivo HomeViewModel.kt no pacote br.com.softmind.ui.viewmodel
package br.com.softmind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.softmind.database.facade.SurveyDatabaseFacade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val surveyDatabaseFacade: SurveyDatabaseFacade
) : ViewModel() {
    
    private val _hasCompletedTodayCheckin = MutableStateFlow<Boolean>(false)
    val hasCompletedTodayCheckin: StateFlow<Boolean> = _hasCompletedTodayCheckin
    
    private val _todaySelectedEmoji = MutableStateFlow<String?>(null)
    val todaySelectedEmoji: StateFlow<String?> = _todaySelectedEmoji
    
    init {
        checkTodayCheckin()
    }
    
    fun checkTodayCheckin() {
        viewModelScope.launch {
            _hasCompletedTodayCheckin.value = surveyDatabaseFacade.hasCompletedTodayCheckin()
            if (_hasCompletedTodayCheckin.value) {
                _todaySelectedEmoji.value = surveyDatabaseFacade.getTodaySelectedEmoji()
            }
        }
    }
}