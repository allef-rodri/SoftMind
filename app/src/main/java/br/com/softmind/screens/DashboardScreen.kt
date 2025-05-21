package br.com.softmind.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import br.com.softmind.navigation.NavRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.navigation.NavHostController


@OptIn(ExperimentalTextApi::class, ExperimentalAnimationApi::class)
@Composable
fun DashboardScreen(
    navController: NavHostController, selectedEmoji: String) {
    val validEmojis = listOf("feliz", "cansado", "triste", "ansioso", "medo", "raiva")
    val isValidEmoji = selectedEmoji in validEmojis

    if (!isValidEmoji) {
        // Tela de erro se não tiver emoji selecionado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4A148C),
                            Color(0xFF6A1B9A),
                            Color(0xFF8E24AA),
                            Color(0xFF9C27B0)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Ops! Algo deu errado.",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Você precisa selecionar um emoji para visualizar seu dashboard.",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { navController.navigate(NavRoutes.HOME) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEC407A)
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Voltar e selecionar um emoji",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    } else {
        DisposableEffect(Unit) {
            onDispose { }
        }

        var isLoaded by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val contentAlpha by animateFloatAsState(
            targetValue = if (isLoaded) 1f else 0f,
            animationSpec = tween(durationMillis = 800)
        )

        val deepPurple = Color(0xFF4A148C)
        val accentPink = Color(0xFFEC407A)
        val accentBlue = Color(0xFF42A5F5)
        val positiveGreen = Color(0xFF4CAF50)
        val negativeRed = Color(0xFFE57373)

        val backgroundGradient = Brush.verticalGradient(
            colors = listOf(
                deepPurple,
                Color(0xFF6A1B9A),
                Color(0xFF8E24AA),
                Color(0xFF9C27B0)
            )
        )

        val moodData = remember { listOf(6, 4, 7, 3, 8, 9, 5) }
        val weekDays = remember { listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom") }

        val positiveDays = remember { moodData.count { it >= 6 } }
        val negativeDays = remember { moodData.count { it < 6 } }
        val averageMood = remember { moodData.average().roundToInt() }
        val mostFrequentMood = remember {
            when (moodData.groupBy { it }.maxByOrNull { it.value.size }?.key) {
                in 1..3 -> "Triste"
                in 4..6 -> "Neutro"
                else -> "Feliz"
            }
        }

        LaunchedEffect(Unit) {
            delay(300)
            isLoaded = true
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient),
            contentAlignment = Alignment.TopCenter
        ) {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .alpha(contentAlpha)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Evolução Emocional - $selectedEmoji",
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.White,
                                accentPink.copy(alpha = 0.9f)
                            )
                        ),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.15f)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.2f),
                                        Color.White.copy(alpha = 0.05f)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Acompanhe sua jornada emocional ao longo do tempo. " +
                                    "Estes dados são baseados nos seus registros diários e ajudam a " +
                                    "identificar padrões para melhorar seu bem-estar.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.15f)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.2f),
                                        Color.White.copy(alpha = 0.05f)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Humor nos Últimos 7 Dias",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        val maxMoodValue = 10f

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                        ) {
                            // Eixo Y
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(end = 4.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.End
                            ) {
                                for (i in maxMoodValue.toInt() downTo 0 step 2) {
                                    Text(
                                        text = i.toString(),
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.width(24.dp)
                                    )
                                }
                            }

                            // Eixo X
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                ) {
                                    Canvas(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                                        val width = size.width
                                        val height = size.height
                                        val barWidth = width / (moodData.size * 1.5f)
                                        val spaceBetween = (width - (barWidth * moodData.size)) / (moodData.size - 1)

                                        for (i in 0..4) {
                                            val y = height - (height * (i / 4f))
                                            drawLine(
                                                color = Color.White.copy(alpha = 0.2f),
                                                start = Offset(0f, y),
                                                end = Offset(width, y),
                                                strokeWidth = 1f
                                            )
                                        }

                                        val points = moodData.mapIndexed { index, value ->
                                            val x = index * (barWidth + spaceBetween) + (barWidth / 2)
                                            val y = height - (height * (value / maxMoodValue))
                                            Offset(x, y)
                                        }

                                        for (i in 0 until points.size - 1) {
                                            drawLine(
                                                color = accentPink,
                                                start = points[i],
                                                end = points[i + 1],
                                                strokeWidth = 3f
                                            )
                                        }

                                        points.forEach { point ->
                                            drawCircle(
                                                color = Color.White,
                                                center = point,
                                                radius = 8f
                                            )
                                            drawCircle(
                                                color = accentPink,
                                                center = point,
                                                radius = 6f
                                            )
                                        }

                                        val fillPath = Path().apply {
                                            moveTo(points.first().x, height)
                                            points.forEach { lineTo(it.x, it.y) }
                                            lineTo(points.last().x, height)
                                            close()
                                        }

                                        drawPath(
                                            path = fillPath,
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    accentPink.copy(alpha = 0.5f),
                                                    accentPink.copy(alpha = 0.1f)
                                                )
                                            )
                                        )
                                    }
                                }

                                // Eixo X (dias da semana)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    weekDays.forEach { day ->
                                        Text(
                                            text = day,
                                            color = Color.White.copy(alpha = 0.7f),
                                            fontSize = 12.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }

                        // Legenda
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(accentPink, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Nível de humor (1-10)",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Dias Positivos",
                        value = positiveDays.toString(),
                        icon = Icons.Rounded.SentimentSatisfied,
                        color = positiveGreen
                    )

                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Dias Negativos",
                        value = negativeDays.toString(),
                        icon = Icons.Rounded.SentimentDissatisfied,
                        color = negativeRed
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Humor Médio",
                        value = "$averageMood/10",
                        icon = Icons.Rounded.Analytics,
                        color = accentBlue
                    )

                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Mais Frequente",
                        value = mostFrequentMood,
                        icon = Icons.Rounded.Favorite,
                        color = accentPink
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.15f)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.2f),
                                        Color.White.copy(alpha = 0.05f)
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Lightbulb,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 16.dp)
                        )
                        Text(
                            text = "Dica: Registre seu humor diariamente para obter insights mais precisos sobre seus padrões emocionais.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val buttonScale by animateFloatAsState(
                    targetValue = if (isPressed) 0.96f else 1f,
                    animationSpec = tween(durationMillis = 100)
                )

                val primaryPurple = Color(0xFF7B1FA2)
                val buttonGradient = Brush.linearGradient(
                    colors = listOf(
                        primaryPurple,
                        accentPink.copy(alpha = 0.8f),
                        primaryPurple
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(300f, 300f)
                )

                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(0.8f)
                        .height(56.dp)
                        .scale(buttonScale)
                        .graphicsLayer {
                            shadowElevation = 16f
                            shape = RoundedCornerShape(28.dp)
                            clip = true
                        }
                        .background(buttonGradient, RoundedCornerShape(28.dp))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            scope.launch {
                                delay(100)
                                navController.navigate(NavRoutes.ALERTS)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Ver Alertas",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.2f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color.copy(alpha = 0.2f),
                        CircleShape
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                color.copy(alpha = 0.7f),
                                color.copy(alpha = 0.3f)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

