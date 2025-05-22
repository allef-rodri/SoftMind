package br.com.softmind.database.util

import androidx.room.TypeConverter
import br.com.softmind.database.enum.QuestionType

class QuestionTypeConverter {
    @TypeConverter
    fun fromQuestionType(type: QuestionType?): String? {
        return type?.name
    }
    
    @TypeConverter
    fun toQuestionType(typeName: String?): QuestionType? {
        return typeName?.let { QuestionType.valueOf(it) }
    }
}