package br.com.softmind.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey
    val sessionId: UUID = UUID.randomUUID(),
    val date: Long = System.currentTimeMillis()
)
