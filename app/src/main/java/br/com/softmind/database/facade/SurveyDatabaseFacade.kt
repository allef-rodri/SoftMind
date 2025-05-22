package br.com.softmind.database.facade

import br.com.softmind.database.dao.SurveyDao
import br.com.softmind.database.entity.Answer
import br.com.softmind.database.entity.Category
import br.com.softmind.database.entity.Option
import br.com.softmind.database.entity.Question
import br.com.softmind.database.enum.QuestionType
import br.com.softmind.database.relation.QuestionWithOptionsAndAnswers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class SurveyDatabaseFacade(private val surveyDao: SurveyDao) {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun populateDatabase(scope: CoroutineScope) {
        scope.launch {
            withContext(Dispatchers.IO) {
                // Cria uma categoria
                val categoryId = UUID.randomUUID()
                val category = Category(
                    categoryId = categoryId,
                    name = "checkin",
                    description = "Perguntas utilizadas para a fase de checkin do app SoftMind",
                    iconUrl = null,
                    colorHex = null,
                    displayOrder = 1
                )

                // Insere a categoria
                surveyDao.insertCategory(category)

                // Cria uma pergunta
                val questionId = UUID.randomUUID()
                val question = Question(
                    questionId = questionId,
                    categoryId = categoryId,
                    title = "Escolha o seu emoji de hoje!",
                    description = "Mapeamento de Riscos - Ansiedade/Depressão/Burnout",
                    type = QuestionType.SINGLE_CHOICE,
                )

                // Insere a pergunta
                surveyDao.insertQuestion(question)

                // Cria 6 opções de resposta
                val options = listOf(
                    Option(
                        optionId = UUID.randomUUID(),
                        questionId = questionId,
                        text = "feliz",
                        value = "happyface",
                        displayOrder = 1
                    ),
                    Option(
                        optionId = UUID.randomUUID(),
                        questionId = questionId,
                        text = "Cansado",
                        value = "tired",
                        displayOrder = 2
                    ),
                    Option(
                        optionId = UUID.randomUUID(),
                        questionId = questionId,
                        text = "triste",
                        value = "sadface",
                        displayOrder = 3
                    ),
                    Option(
                        optionId = UUID.randomUUID(),
                        questionId = questionId,
                        text = "ansioso",
                        value = "scare",
                        displayOrder = 4
                    ),
                    Option(
                        optionId = UUID.randomUUID(),
                        questionId = questionId,
                        text = "medo",
                        value = "scared",
                        displayOrder = 5
                    ),
                    Option(
                        optionId = UUID.randomUUID(),
                        questionId = questionId,
                        text = "raiva",
                        value = "angry",
                        displayOrder = 6
                    )
                )

                // Insere as opções
                surveyDao.insertOptions(options)
            }
        }
    }

    fun clearDatabase(scope: CoroutineScope) {
        scope.launch {
            withContext(Dispatchers.IO) {
                val categories = surveyDao.getAllCategories().first()
                categories.forEach { category ->
                    surveyDao.deleteCategory(category)
                }
            }
        }
    }

    suspend fun isDatabasePopulated(): Boolean {
        return withContext(Dispatchers.IO) {
            val categories = surveyDao.getAllCategories().first()
            categories.isNotEmpty()
        }
    }

    suspend fun saveAnswer(questionId: UUID, selectedOptionId: UUID, sessionDate: String = getCurrentSessionDate()) {
        withContext(Dispatchers.IO) {
            val answer = Answer(
                answerId = UUID.randomUUID(),
                questionId = questionId,
                selectedOptionId = selectedOptionId,
                sessionDate = sessionDate,
                answeredAt = Date()
            )
            surveyDao.insertAnswer(answer)
        }
    }

    suspend fun saveTextAnswer(questionId: UUID, textValue: String, sessionDate: String = getCurrentSessionDate()) {
        withContext(Dispatchers.IO) {
            val answer = Answer(
                answerId = UUID.randomUUID(),
                questionId = questionId,
                textValue = textValue,
                sessionDate = sessionDate,
                answeredAt = Date()
            )
            surveyDao.insertAnswer(answer)
        }
    }

    suspend fun saveNumericAnswer(questionId: UUID, intValue: Int, sessionDate: String = getCurrentSessionDate()) {
        withContext(Dispatchers.IO) {
            val answer = Answer(
                answerId = UUID.randomUUID(),
                questionId = questionId,
                intValue = intValue,
                sessionDate = sessionDate,
                answeredAt = Date()
            )
            surveyDao.insertAnswer(answer)
        }
    }

    suspend fun getAnswersBySession(sessionDate: String = getCurrentSessionDate()) =
        surveyDao.getAnswersBySession(sessionDate)

    suspend fun getAnswerForQuestion(questionId: UUID, sessionDate: String = getCurrentSessionDate()) =
        surveyDao.getAnswerForQuestion(questionId, sessionDate)

    suspend fun getQuestionsWithOptionsForCategory(categoryId: UUID) =
        surveyDao.getQuestionsWithOptionsAndAnswersByCategory(categoryId)

    fun getAllCategories(): Flow<List<Category>> {
        return surveyDao.getAllCategories()
    }

    suspend fun getCategoryByNameAndOrder(name: String, displayOrder: Int): Category? {
        return withContext(Dispatchers.IO) {
            surveyDao.getCategoryByNameAndOrder(name, displayOrder)
        }
    }

    suspend fun hasCompletedTodayCheckin(): Boolean {
        return withContext(Dispatchers.IO) {
            // Obter a categoria checkin
            val checkinCategory = getCategoryByNameAndOrder(name = "checkin", displayOrder = 1)

            if (checkinCategory != null) {
                // Obter as perguntas da categoria
                val questions = surveyDao.getQuestionsByCategory(checkinCategory.categoryId).first()

                if (questions.isNotEmpty()) {
                    // Verificar se há respostas para essas perguntas hoje
                    val today = dateFormatter.format(Date())
                    val firstQuestion = questions.first()
                    val answer = surveyDao.getAnswerForQuestion(firstQuestion.questionId, today)

                    // Se encontrou uma resposta, o usuário já completou o checkin hoje
                    answer != null
                } else {
                    false
                }
            } else {
                false
            }
        }
    }

    suspend fun getTodaySelectedEmoji(): String? {
        return withContext(Dispatchers.IO) {
            // Obter a categoria checkin
            val checkinCategory = getCategoryByNameAndOrder(name = "checkin", displayOrder = 1)

            if (checkinCategory != null) {
                // Obter as perguntas da categoria
                val questions = surveyDao.getQuestionsByCategory(checkinCategory.categoryId).first()

                if (questions.isNotEmpty()) {
                    // Verificar se há respostas para essas perguntas hoje
                    val today = dateFormatter.format(Date())
                    val firstQuestion = questions.first()
                    val answer = surveyDao.getAnswerForQuestion(firstQuestion.questionId, today)

                    if (answer != null && answer.selectedOptionId != null) {
                        // Obter a opção selecionada
                        val options = surveyDao.getOptionsByQuestionId(firstQuestion.questionId).first()
                        val selectedOption = options.find { it.optionId == answer.selectedOptionId }
                        selectedOption?.text
                    } else {
                        null
                    }
                } else {
                    null
                }
            } else {
                null
            }
        }
    }

    /**
     * Sincroniza perguntas e opções do banco externo para o local
     */
    suspend fun syncQuestionsFromRemote(remoteQuestions: List<Question>, remoteOptions: Map<UUID, List<Option>>) {
        withContext(Dispatchers.IO) {
            // Obter questões locais atuais
            val localQuestions = surveyDao.getAllQuestions().first()

            // Processamento de questões...
            for (remoteQuestion in remoteQuestions) {
                val existingQuestion = localQuestions.find { it.questionId == remoteQuestion.questionId }

                if (existingQuestion != null) {
                    // Atualizar questão existente
                    surveyDao.updateQuestion(remoteQuestion)
                } else {
                    // Inserir nova questão
                    surveyDao.insertQuestion(remoteQuestion)
                }

                // Processar opções para esta questão
                val options = remoteOptions[remoteQuestion.questionId] ?: continue

                // Obter opções locais atuais para esta questão
                val localOptionsFlow = surveyDao.getOptionsByQuestionId(remoteQuestion.questionId)
                val localOptions = localOptionsFlow.first()

                // Atualizar/inserir opções
                for (option in options) {
                    val existingOption = localOptions.find { it.optionId == option.optionId }
                    if (existingOption != null) {
                        surveyDao.updateOption(option)
                    } else {
                        surveyDao.insertOption(option)
                    }
                }

                // Remover opções que não existem mais remotamente
                val remoteOptionIds = options.map { it.optionId }
                val optionsToDelete = localOptions.filter { it.optionId !in remoteOptionIds }
                for (option in optionsToDelete) {
                    surveyDao.deleteOption(option)
                }
            }

            // Remover questões que não existem mais remotamente
            val remoteQuestionIds = remoteQuestions.map { it.questionId }
            val questionsToDelete = localQuestions.filter { it.questionId !in remoteQuestionIds }
            for (question in questionsToDelete) {
                surveyDao.deleteQuestion(question)
            }
        }
    }

    private fun getCurrentSessionDate(): String {
        return dateFormatter.format(Date())
    }
}