package br.com.softmind.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "answers",
    foreignKeys = [
        ForeignKey(
            entity = Question::class,
            parentColumns = ["questionId"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["questionId", "sessionDate"], unique = true),
        Index(value = ["questionId"])
    ]
)
data class Answer(
    @PrimaryKey
    val answerId: UUID = UUID.randomUUID(),
    val questionId: UUID,
    val selectedOptionId: UUID? = null,
    val textValue: String? = null,
    val intValue: Int? = null,
    val sessionDate: String,
    val answeredAt: Date = Date()
)