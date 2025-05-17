package br.com.softmind.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.*
import kotlin.random.Random

@Composable
fun HomeScreen(onStartClick: () -> Unit = {}) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("animationhome.json")
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    // Detecta dark mode
    val isDark = isSystemInDarkTheme()

    val screenBackground = if (isDark) {
        Brush.verticalGradient(
            colors = listOf(Color(0xFF1B1B1B), Color(0xFF3B3B3B))
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(Color(0xFF512DA8), Color(0xFF9575CD))
        )
    }

    val cardGradient = if (isDark) {
        Brush.verticalGradient(
            colors = listOf(Color(0xFF4A148C), Color(0xFF6A1B9A))
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(Color(0xFF7E57C2), Color(0xFFD1C4E9))
        )
    }


    val frases = listOf(
        "Você é mais forte do que imagina.",
        "Respire. Sinta. Recomece.",
        "Um passo de cada vez, sempre em frente.",
        "Cuidar de si é um ato de coragem.",
        "Tudo bem não estar bem. Você não está sozinho.",
        "Hoje é um bom dia para se acolher.",
        "A sua saúde mental importa — e muito!"
    )

    val fraseAleatoria = remember {
        frases[Random.nextInt(frases.size)]
    }

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = screenBackground),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(600)) +
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight / 4 },
                        animationSpec = tween(600)
                    )
        ) {
            Card(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = Color.White.copy(alpha = 0.35f),
                        spotColor = Color.White.copy(alpha = 0.35f)
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFFE1BEE7),
                        shape = RoundedCornerShape(24.dp)
                    ),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(brush = cardGradient, shape = RoundedCornerShape(24.dp))
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottieAnimation(
                            composition = composition,
                            progress = progress,
                            modifier = Modifier.size(200.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Bem-vindo ao SoftMind",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = fraseAleatoria,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFEDE7F6),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(48.dp)) // aumento de espaço acima do botão

                        val interactionSource = remember { MutableInteractionSource() }
                        val isPressed by interactionSource.collectIsPressedAsState()
                        val scale by animateFloatAsState(
                            targetValue = if (isPressed) 0.97f else 1f,
                            animationSpec = tween(durationMillis = 100),
                            label = "buttonScale"
                        )

                        Button(
                            onClick = onStartClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8E24AA)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .graphicsLayer(scaleX = scale, scaleY = scale)
                                .fillMaxWidth()
                                .height(50.dp),
                            interactionSource = interactionSource
                        ) {
                            Text(
                                text = "Começar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
