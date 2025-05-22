package br.com.softmind.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.softmind.database.enum.QuestionType
import java.util.UUID

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["categoryId"])
    ]
)
data class Question(
    @PrimaryKey
    val questionId: UUID = UUID.randomUUID(),
    val categoryId: UUID,
    val title: String,
    val description: String? = null,
    val type: QuestionType
)
