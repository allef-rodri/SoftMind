package br.com.softmind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.softmind.database.facade.SurveyDatabaseFacade

class HomeViewModelFactory(private val surveyDatabaseFacade: SurveyDatabaseFacade) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(surveyDatabaseFacade) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}