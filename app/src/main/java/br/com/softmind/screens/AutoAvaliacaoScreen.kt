package br.com.softmind.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.softmind.navigation.NavRoutes
import br.com.softmind.viewmodel.QuestionarioViewModel
import br.com.softmind.model.QuestaoResponse
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import kotlinx.coroutines.delay

@Composable
fun AutoAvaliacaoScreen(
    navController: NavHostController,
    viewModel: QuestionarioViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.carregarQuestionario()
    }

    val isLoading by viewModel.isLoading.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    when {
        // Exibe mensagem de erro com botão de retry
        errorMessage != null -> {
            QuestionarioErrorContent(
                message = errorMessage!!,
                onRetry = { viewModel.retryCarregarQuestionario() },
                onBack = { navController.popBackStack() }
            )
        }
        // Estado de carregamento
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        // Conteúdo principal ou mensagem de lista vazia
        else -> {
            categorias?.let { listaCategorias ->
                val todasQuestoes = listaCategorias.flatMap { it.questoes }
                if (todasQuestoes.isNotEmpty()) {
                    Questionario(
                        questions = todasQuestoes,
                        navController = navController,
                        viewModel = viewModel
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhuma questão disponível",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Questionario(
    questions: List<QuestaoResponse>,
    navController: NavHostController,
    viewModel: QuestionarioViewModel
) {
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
                ResultScreen(
                    answers = answers,
                    questions = questions,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun QuestionScreen(
    questao: QuestaoResponse,
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
                text = questao.texto,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            questao.opcoes.forEach { option ->
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
fun ResultScreen(
    answers: List<String>,
    questions: List<QuestaoResponse>,
    navController: NavHostController,
    viewModel: QuestionarioViewModel
) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4A148C),
            Color(0xFF6A1B9A),
            Color(0xFF8E24AA),
            Color(0xFF9C27B0)
        )
    )

    LaunchedEffect(Unit) {
        val respostasComPerguntas = questions.zip(answers) { pergunta, resposta ->
            pergunta.texto to resposta
        }
        viewModel.enviarRespostas(respostasComPerguntas)

        delay(4000)
        navController.navigate(NavRoutes.HOME) {
            popUpTo("autoavaliacao") { inclusive = true }
        }
    }

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

@Composable
private fun QuestionarioErrorContent(
    message: String,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
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
            .background(backgroundGradient)
    ) {
        // Cabeçalho com botão de voltar semelhante ao da tela de alertas
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp)
        ) {
            val accentBlue = Color(0xFF42A5F5)
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = accentBlue
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        // Conteúdo central com a mensagem de erro
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.15f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = message,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onRetry,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFA000)
                        )
                    ) {
                        Text(
                            text = "Tentar novamente",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
