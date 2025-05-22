package br.com.softmind.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val categoryId: UUID = UUID.randomUUID(),
    val name: String,
    val description: String? = null,
    val iconUrl: String? = null,
    val colorHex: String? = null,
    val displayOrder: Int
)
