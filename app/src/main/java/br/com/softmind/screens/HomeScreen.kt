package br.com.softmind.screens

import androidx.activity.compose.LocalActivity
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
    
    val scope = rememberCoroutineScope()
    
    var isLoaded by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(300)
        isLoaded = true
        viewModel.checkTodayCheckin()
    }
    
    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface
    
    val primaryPurple = Color(0xFF7B1FA2)
    val deepPurple = Color(0xFF4A148C)
    val accentPink = Color(0xFFEC407A)
    
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            deepPurple,
            Color(0xFF6A1B9A),
            Color(0xFF8E24AA),
            Color(0xFF9C27B0)
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
    
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("mental_health.json")
    )
    
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            AnimatedVisibility(
                visible = isLoaded,
                enter = fadeIn() + slideInVertically { -40 },
                exit = fadeOut()
            ) {
                Text(
                    text = "SoftMind",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF7B1FA2),
                                Color(0xFFEC407A)
                            )
                        )
                    ),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Lottie Animation
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Check-in Card
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.15f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (hasCompletedTodayCheckin) 
                               "Você já realizou seu check-in hoje!" 
                               else "Como você está se sentindo hoje?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = if (hasCompletedTodayCheckin) 
                               "Seu estado emocional foi registrado com sucesso." 
                               else "Faça seu check-in diário para registrar seu estado emocional.",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Botões de checkin
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    val buttonScale by animateFloatAsState(
                        targetValue = if (isPressed) 0.95f else 1f,
                        animationSpec = tween(durationMillis = 100)
                    )
                    
                    // Se o usuário já completou o checkin, mostrar o botão para ir direto para o Dashboard
                    if (hasCompletedTodayCheckin && todaySelectedEmoji != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .scale(buttonScale)
                                .graphicsLayer {
                                    shadowElevation = 8f
                                    shape = RoundedCornerShape(25.dp)
                                    clip = true
                                }
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF4CAF50),
                                            Color(0xFF8BC34A)
                                        ),
                                        start = Offset(0f, 0f),
                                        end = Offset(300f, 300f)
                                    ),
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    scope.launch {
                                        delay(100)
                                        // Aqui usamos explicitamente o valor do todaySelectedEmoji
                                        val emoji = todaySelectedEmoji ?: ""
                                        navController.navigate("${NavRoutes.DASHBOARD}/$emoji")
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Ver meu dashboard",
                                    fontSize = 16.sp,
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .scale(buttonScale)
                            .graphicsLayer {
                                shadowElevation = 8f
                                shape = RoundedCornerShape(25.dp)
                                clip = true
                            }
                            .background(
                                brush = buttonGradient,
                                shape = RoundedCornerShape(25.dp)
                            )
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                scope.launch {
                                    delay(100)
                                    navController.navigate(NavRoutes.CHECKIN)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (hasCompletedTodayCheckin) "Refazer checkin" else "Fazer checkin diário",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            
            // Espaçador que empurra os ícones para o rodapé
            Spacer(modifier = Modifier.weight(1f))
            
            // Features grid no rodapé
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Recursos disponíveis",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeatureIcon(
                        icon = Icons.Rounded.Notifications,
                        label = "Alertas",
                        color = Color(0xFF00BCD4),
                        onClick = { navController.navigate(NavRoutes.ALERTS) }
                    )
                    
                    FeatureIcon(
                        icon = Icons.Rounded.Mail,
                        label = "Suporte",
                        color = Color(0xFF8BC34A),
                        onClick = { navController.navigate(NavRoutes.SUPPORT) }
                    )
                    
                    FeatureIcon(
                        icon = Icons.Rounded.Favorite,
                        label = "Bem-estar",
                        color = Color(0xFFFF5722),
                        onClick = { navController.navigate(NavRoutes.WELLNESS) }
                    )
                }
                
                // Espaço no final para evitar que os ícones fiquem muito no limite da tela
                Spacer(modifier = Modifier.height(16.dp))
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
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}