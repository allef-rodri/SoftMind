package br.com.softmind.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WellnessScreen(navController: NavHostController) {
    var isLoaded by remember { mutableStateOf(false) }

    val primaryPurple = Color(0xFF7B1FA2)
    val deepPurple = Color(0xFF4A148C)
    val accentPink = Color(0xFFEC407A)
    val accentBlue = Color(0xFF42A5F5)

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            deepPurple,
            Color(0xFF6A1B9A),
            Color(0xFF8E24AA),
            Color(0xFF9C27B0)
        )
    )

    LaunchedEffect(Unit) {
        delay(300)
        isLoaded = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Rounded.Spa,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            AnimatedVisibility(
                visible = isLoaded,
                enter = fadeIn(tween(1000)) + expandVertically(tween(800, easing = EaseOutQuart))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .graphicsLayer {
                            shadowElevation = 8f
                            shape = RoundedCornerShape(24.dp)
                            clip = true
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.15f)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.2f),
                                        accentPink.copy(alpha = 0.1f)
                                    )
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Spa,
                                    contentDescription = null,
                                    tint = accentPink,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Orientações de Bem-Estar",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            val orientations = listOf(
                                Pair(Icons.Rounded.Air, "Pratique a respiração profunda por 5 minutos diariamente."),
                                Pair(Icons.Rounded.Bedtime, "Mantenha uma rotina de sono regular, dormindo 7-8 horas por noite."),
                                Pair(Icons.Rounded.BreakfastDining, "Faça pausas regulares durante o trabalho para relaxar a mente."),
                                Pair(Icons.Rounded.SelfImprovement, "Dedique tempo para atividades que você gosta e trazem alegria."),
                                Pair(Icons.Rounded.People, "Conecte-se com pessoas que te apoiam e fazem você se sentir bem.")
                            )

                            orientations.forEachIndexed { index, (icon, text) ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                        .graphicsLayer {
                                            shadowElevation = 4f
                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White.copy(alpha = 0.1f)
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .background(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(
                                                            accentPink.copy(alpha = 0.7f),
                                                            primaryPurple.copy(alpha = 0.5f)
                                                        )
                                                    ),
                                                    shape = CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(
                                            text = text,
                                            color = Color.White.copy(alpha = 0.9f),
                                            fontSize = 15.sp,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }

                                if (index < orientations.size - 1) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = isLoaded,
                enter = fadeIn(tween(1200)) + expandVertically(tween(1000, easing = EaseOutQuart))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .graphicsLayer {
                            shadowElevation = 8f
                            shape = RoundedCornerShape(24.dp)
                            clip = true
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.15f)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.2f),
                                        accentBlue.copy(alpha = 0.1f)
                                    )
                                )
                            )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(60.dp)
                    .alpha(0.6f)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                accentPink.copy(alpha = 0.7f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
