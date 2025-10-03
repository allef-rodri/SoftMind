package br.com.softmind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.softmind.database.entity.Option
import br.com.softmind.database.entity.Question
import br.com.softmind.database.facade.SurveyDatabaseFacade
import br.com.softmind.repository.CheckinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CheckinViewModel(
    private val surveyDatabaseFacade: SurveyDatabaseFacade
) : ViewModel() {
    private val _emojiOptions = MutableStateFlow<List<Option>>(emptyList())
    val emojiOptions: StateFlow<List<Option>> = _emojiOptions

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion

    private val _selectedOption = MutableStateFlow<Option?>(null)
    val selectedOption: StateFlow<Option?> = _selectedOption

    private val repository = CheckinRepository()

    // Expõe mensagens de erro ocorridas durante o processo de salvar o humor.
    private val _errorMessage = MutableStateFlow<String?>(null)
    /**
     * Fluxo que a interface pode observar para exibir mensagens de erro.
     */
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadCheckinData()
    }

    fun selectOption(option: Option) {
        _selectedOption.value = option
    }

    fun saveAnswer(onComplete: () -> Unit) {
        viewModelScope.launch {
            val question = _currentQuestion.value
            val option = _selectedOption.value

            if (question != null && option != null) {
                try {
                    surveyDatabaseFacade.saveAnswer(question.questionId, option.optionId)
                    repository.salvarHumor(emojiName = option.value.toString())
                    onComplete()
                } catch (e: Exception) {
                    // Define a mensagem de erro para que a UI possa reagir
                    // Mapeia a mensagem de erro para algo mais amigável ao usuário
                    val friendlyMessage = when {
                        e.message?.contains("Unable to resolve host") == true ->
                            "Você está offline ou há problemas com a rede. Verifique sua conexão e tente novamente."
                        e.message?.contains("timeout") == true ->
                            "A solicitação demorou demais. Tente novamente."
                        else ->
                            "Não foi possível salvar sua resposta. Por favor, tente novamente."
                    }
                    _errorMessage.value = friendlyMessage
                }
            }
        }
    }

    /**
     * Permite que a interface limpe a mensagem de erro após exibi-la ao usuário.
     */
    fun clearError() {
        _errorMessage.value = null
    }

    private fun loadCheckinData() {
        viewModelScope.launch {
            try {
                // Obter a categoria de checkin
                val checkinCategory = surveyDatabaseFacade.getCategoryByNameAndOrder(
                    name = "checkin",
                    displayOrder = 1
                )

                checkinCategory?.let { category ->
                    // Obter as perguntas com opções para a categoria de checkin
                    val questionsWithOptions = surveyDatabaseFacade
                        .getQuestionsWithOptionsForCategory(category.categoryId)
                        .first()

                    // Pegar a primeira pergunta (a dos emojis)
                    val questionWithOptions = questionsWithOptions.firstOrNull()

                    questionWithOptions?.let {
                        _currentQuestion.value = it.question
                        _emojiOptions.value = it.options
                    }
                }
            } catch (e: Exception) {
                // Lidar com erros - poderia atualizar um estado de erro aqui
                println("Erro ao carregar dados de checkin: ${e.message}")
            }
        }
    }
}