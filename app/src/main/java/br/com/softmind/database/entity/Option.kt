package br.com.softmind.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "options",
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
        Index(value = ["questionId"])
    ]
)
data class Option(
    @PrimaryKey
    val optionId: UUID = UUID.randomUUID(),
    val questionId: UUID,
    val text: String,
    val value: String? = null,
    val displayOrder: Int,
    val imageUrl: String? = null
)