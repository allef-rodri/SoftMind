package br.com.softmind.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import br.com.softmind.database.entity.Option
import br.com.softmind.database.entity.Question

data class QuestionWithOptions(
    @Embedded
    val question: Question,

    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val options: List<Option>
)