package br.com.softmind.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "checkin_responses",
    foreignKeys = [
        ForeignKey(
            entity = Session::class,
            parentColumns = ["sessionId"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("sessionId"),
        Index("questionId")
    ]
)
data class CheckInResponse(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val sessionId: UUID,
    val questionId: UUID,
    val answer: String,
    val timeStamp: Long = System.currentTimeMillis()
)
