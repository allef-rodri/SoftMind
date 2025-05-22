package br.com.softmind.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.softmind.database.entity.Answer
import br.com.softmind.database.entity.Category
import br.com.softmind.database.entity.Option
import br.com.softmind.database.entity.Question
import br.com.softmind.database.relation.CategoryWithQuestions
import br.com.softmind.database.relation.QuestionWithAnswers
import br.com.softmind.database.relation.QuestionWithOptionsAndAnswers
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface SurveyDao {
    // Category operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories ORDER BY displayOrder")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    suspend fun getCategoryById(categoryId: UUID): Category?

    @Query("SELECT * FROM categories WHERE name = :name AND displayOrder = :displayOrder LIMIT 1")
    suspend fun getCategoryByNameAndOrder(name: String, displayOrder: Int): Category?

    // Question operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question): Long

    @Update
    suspend fun updateQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE questionId = :questionId")
    suspend fun getQuestionById(questionId: UUID): Question?

    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    fun getQuestionsByCategory(categoryId: UUID): Flow<List<Question>>

    // Option operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOption(option: Option): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptions(options: List<Option>)

    @Update
    suspend fun updateOption(option: Option)

    @Delete
    suspend fun deleteOption(option: Option)

    @Query("SELECT * FROM options WHERE questionId = :questionId")
    fun getOptionsByQuestionId(questionId: UUID): Flow<List<Option>>

    // Answer operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: Answer): Long

    @Update
    suspend fun updateAnswer(answer: Answer)

    @Delete
    suspend fun deleteAnswer(answer: Answer)

    @Query("SELECT * FROM answers WHERE sessionDate = :sessionDate")
    fun getAnswersBySession(sessionDate: String): Flow<List<Answer>>

    @Query("SELECT * FROM answers WHERE questionId = :questionId AND sessionDate = :sessionDate")
    suspend fun getAnswerForQuestion(questionId: UUID, sessionDate: String): Answer?

    // Relations queries
    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithQuestions(): Flow<List<CategoryWithQuestions>>

    @Transaction
    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    fun getCategoryWithQuestions(categoryId: UUID): Flow<CategoryWithQuestions>

    @Transaction
    @Query("SELECT * FROM questions WHERE questionId = :questionId")
    suspend fun getQuestionWithAnswers(questionId: UUID): QuestionWithAnswers?

    @Transaction
    @Query("SELECT * FROM questions WHERE questionId = :questionId")
    suspend fun getQuestionWithOptionsAndAnswers(questionId: UUID): QuestionWithOptionsAndAnswers?

    @Transaction
    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    fun getQuestionsWithOptionsAndAnswersByCategory(categoryId: UUID): Flow<List<QuestionWithOptionsAndAnswers>>
}
