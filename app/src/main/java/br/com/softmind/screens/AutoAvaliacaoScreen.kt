package br.com.softmind.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AutoAvaliacaoScreen() {
    val questoes = listOf(
        questao(
            1,
            "Como você se sente hoje?",
            listOf("Motivado", "Cansado", "Preocupado", "Estressado", "Animado", "Satisfeito")
        ),
        questao(
            2,
            "Como você avalia sua carga de trabalho?",
            listOf("Muito Leve", "Leve", "Média", "Alta", "Muito Alta")
        ),
        questao(
            3,
            "Sua carga de trabalho afeta sua qualidade de vida?",
            listOf("Não", "Raramente", "Às vezes", "Frequentemente", "Sempre")
        ),
        questao(
            4,
            "Você trabalha além do seu horário regular?",
            listOf("Não", "Raramente", "Às vezes", "Frequentemente", "Sempre")
        ),
        questao(
            5,
            "Você tem apresentado sintomas como insônia, irritabilidade ou cansaço extremo?",
            listOf("Nunca", "Raramente", "Às vezes", "Frequentemente", "Sempre")
        ),
        questao(
            6,
            "Você sente que sua saúde mental prejudica sua produtividade no trabalho?",
            listOf("Nunca", "Raramente", "Às vezes", "Frequentemente", "Sempre")
        )
    )


    Questionario(questoes)
}

data class questao(
    val id: Int,
    val text: String,
    val options: List<String>
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Questionario(questions: List<questao>) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    val answers = remember { mutableStateListOf<String>() }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4A148C),
            Color(0xFF6A1B9A),
            Color(0xFF8E24AA),
            Color(0xFF9C27B0)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = currentQuestionIndex,
            transitionSpec = {
                slideInHorizontally { width -> width } + fadeIn() with
                        slideOutHorizontally { width -> -width } + fadeOut()
            },
            label = "question_transition"
        ) { index ->
            if (index < questions.size) {
                QuestionScreen(
                    questao = questions[index],
                    onAnswer = { answer ->
                        answers.add(answer)
                        currentQuestionIndex++
                    }
                )
            } else {
                ResultScreen(answers)
            }
        }
    }
}

@Composable
fun QuestionScreen(
    questao: questao,
    onAnswer: (String) -> Unit
) {
    val cardGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF7E57C2).copy(alpha = 0.3f),
            Color(0xFFD1C4E9).copy(alpha = 0.1f)
        )
    )

    val buttonGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF7B1FA2),
            Color(0xFFEC407A).copy(alpha = 0.8f),
            Color(0xFF7B1FA2)
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(cardGradient)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = questao.text,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            questao.options.forEach { option ->
                Button(
                    onClick = { onAnswer(option) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(buttonGradient, shape = RoundedCornerShape(28.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResultScreen(answers: List<String>) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4A148C),
            Color(0xFF6A1B9A),
            Color(0xFF8E24AA),
            Color(0xFF9C27B0)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Obrigado por responder!",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        answers.forEachIndexed { index, answer ->
            Text(
                text = "Pergunta ${index + 1}: $answer",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AutoAvaliacaoScreenPreview() {
    AutoAvaliacaoScreen()
}
