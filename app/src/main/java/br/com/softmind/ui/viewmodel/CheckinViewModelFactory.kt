package br.com.softmind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.softmind.database.facade.SurveyDatabaseFacade

class CheckinViewModelFactory(private val surveyDatabaseFacade: SurveyDatabaseFacade) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckinViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CheckinViewModel(surveyDatabaseFacade) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}