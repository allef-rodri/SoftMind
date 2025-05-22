package br.com.softmind.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.softmind.database.dao.SurveyDao
import br.com.softmind.database.entity.Answer
import br.com.softmind.database.entity.Category
import br.com.softmind.database.entity.Option
import br.com.softmind.database.entity.Question
import br.com.softmind.database.util.DateConverter
import br.com.softmind.database.util.QuestionTypeConverter
import br.com.softmind.database.util.UuidConverter

@Database(
    entities = [
        Category::class,
        Question::class,
        Option::class,
        Answer::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    UuidConverter::class,
    DateConverter::class,
    QuestionTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "survey_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}