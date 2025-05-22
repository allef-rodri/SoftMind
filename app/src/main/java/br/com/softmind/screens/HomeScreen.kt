package br.com.softmind.screens

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.softmind.MainActivity
import br.com.softmind.navigation.NavRoutes
import br.com.softmind.ui.viewmodel.HomeViewModel
import br.com.softmind.ui.viewmodel.HomeViewModelFactory
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalTextApi::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            (LocalActivity.current as MainActivity).surveyDatabaseFacade
        )
    )
) {
    // Observar os estados do ViewModel
    val hasCompletedTodayCheckin by viewModel.hasCompletedTodayCheckin.collectAsState()
    val todaySelectedEmoji by viewModel.todaySelectedEmoji.collectAsState()
    
    // Configuração da animação Lottie original
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("animationhome.json")
    )

    val lottieLoaded = composition != null

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = lottieLoaded,
        restartOnPlay = false
    )

    var isLoaded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val cardScale by animateFloatAsState(
        targetValue = if (isLoaded) 1f else 0.92f,
        animationSpec = tween(durationMillis = 1000)
    )

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

    val cardGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF7E57C2).copy(alpha = 0.3f),
            Color(0xFFD1C4E9).copy(alpha = 0.1f)
        )
    )

    val buttonGradient = Brush.linearGradient(
        colors = listOf(
            primaryPurple,
            accentPink.copy(alpha = 0.8f),
            primaryPurple
        ),
        start = Offset(0f, 0f),
        end = Offset(300f, 300f)
    )

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

    LaunchedEffect(Unit) {
        delay(300)
        isLoaded = true
        // Verificar se o usuário já fez o checkin hoje
        viewModel.checkTodayCheckin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isLoaded,
            enter = fadeIn(animationSpec = tween(1000)) +
                    slideInVertically(
                        initialOffsetY = { -50 },
                        animationSpec = tween(800)
                    ),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SoftMind",
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.White,
                                accentPink.copy(alpha = 0.9f)
                            )
                        ),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 36.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Seu espaço de bem-estar",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Card principal
        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .scale(cardScale),
            shape = RoundedCornerShape(17.dp),
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
                    .background(brush = cardGradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        accentPink.copy(alpha = 0.2f),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (lottieLoaded) {
                            LottieAnimation(
                                composition = composition,
                                progress = progress,
                                modifier = Modifier.size(200.dp)
                            )
                        } else {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = if (hasCompletedTodayCheckin) 
                               "Seu check-in de hoje já foi registrado!" 
                               else "Bem-vindo ao SoftMind",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.1f)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.FormatQuote,
                                    contentDescription = null,
                                    tint = accentPink.copy(alpha = 0.7f),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (hasCompletedTodayCheckin)
                                           "Obrigado por compartilhar como você está se sentindo hoje."
                                           else fraseAleatoria,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White.copy(alpha = 0.9f),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 24.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Se o usuário já completou o checkin, mostrar botão de ver dashboard
                    if (hasCompletedTodayCheckin && todaySelectedEmoji != null) {
                        val dashboardInteractionSource = remember { MutableInteractionSource() }
                        val isDashboardPressed by dashboardInteractionSource.collectIsPressedAsState()
                        val dashboardButtonScale by animateFloatAsState(
                            targetValue = if (isDashboardPressed) 0.96f else 1f,
                            animationSpec = tween(durationMillis = 100)
                        )
                        
                        Box(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(0.8f)
                                .height(56.dp)
                                .scale(dashboardButtonScale)
                                .graphicsLayer {
                                    shadowElevation = 16f
                                    shape = RoundedCornerShape(28.dp)
                                    clip = true
                                }
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF4CAF50),
                                            Color(0xFF8BC34A)
                                        ),
                                        start = Offset(0f, 0f),
                                        end = Offset(300f, 300f)
                                    ), 
                                    RoundedCornerShape(28.dp)
                                )
                                .clickable(
                                    interactionSource = dashboardInteractionSource,
                                    indication = null
                                ) {
                                    scope.launch {
                                        delay(100)
                                        // Usar explicitamente o valor de todaySelectedEmoji
                                        val emoji = todaySelectedEmoji ?: ""
                                        navController.navigate("${NavRoutes.DASHBOARD}/$emoji")
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Ver meu dashboard",
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
                        
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Botão para fazer/refazer o checkin
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    val buttonScale by animateFloatAsState(
                        targetValue = if (isPressed) 0.96f else 1f,
                        animationSpec = tween(durationMillis = 100)
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
                                    if (hasCompletedTodayCheckin) {
                                        navController.navigate(NavRoutes.AVALIACAO)
                                    } else {
                                        navController.navigate(NavRoutes.CHECKIN)
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (hasCompletedTodayCheckin) "Auto Avaliação" else "Começar",
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

        AnimatedVisibility(
            visible = isLoaded,
            enter = fadeIn(animationSpec = tween(1500)) +
                    slideInVertically(
                        initialOffsetY = { 50 },
                        animationSpec = tween(1000)
                    ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FeatureIcon(
                    icon = Icons.Rounded.Headphones,
                    label = "Escuta",
                    color = accentPink,
                    onClick = { navController.navigate(NavRoutes.SUPPORT) }
                )

                FeatureIcon(
                    icon = Icons.Rounded.Spa,
                    label = "Bem-estar",
                    color = accentBlue,
                    onClick = { navController.navigate(NavRoutes.WELLNESS) }
                )

                FeatureIcon(
                    icon = Icons.Rounded.Notifications,
                    label = "Alertas",
                    color = Color(0xFFFFA000),
                    onClick = { navController.navigate(NavRoutes.ALERTS) }
                )
            }
        }
    }
}

@Composable
fun FeatureIcon(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = color.copy(alpha = 0.2f),
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.7f),
                            Color.White.copy(alpha = 0.2f)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.8f),
            fontWeight = FontWeight.Medium
        )
    }
}