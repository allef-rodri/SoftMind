package br.com.softmind.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import br.com.softmind.database.entity.Answer
import br.com.softmind.database.entity.Option
import br.com.softmind.database.entity.Question

data class QuestionWithOptionsAndAnswers(
    @Embedded
    val question: Question,

    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val options: List<Option>,

    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val answers: List<Answer>
)
