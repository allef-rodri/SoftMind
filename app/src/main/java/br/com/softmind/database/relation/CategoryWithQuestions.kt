package br.com.softmind.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import br.com.softmind.database.entity.Category
import br.com.softmind.database.entity.Question

data class CategoryWithQuestions(
    @Embedded
    val category: Category,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val questions: List<Question>
)