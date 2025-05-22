package br.com.softmind.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class, ExperimentalAnimationApi::class)
@Composable
fun SupportScreen(navController: NavHostController) {
    var isLoaded by remember { mutableStateOf(false) }
    var showContacts by remember { mutableStateOf(false) }

    val titleAlpha by animateFloatAsState(
        targetValue = if (isLoaded) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = EaseOutQuart)
    )

    val cardScale by animateFloatAsState(
        targetValue = if (isLoaded) 1f else 0.95f,
        animationSpec = tween(durationMillis = 800, easing = EaseOutBack)
    )

    val infiniteTransition = rememberInfiniteTransition()
    val buttonScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        )
    )

    val primaryPurple = Color(0xFF7B1FA2)
    val lightPurple = Color(0xFFAB47BC)
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

    val buttonGradient = Brush.linearGradient(
        colors = listOf(
            primaryPurple,
            accentPink.copy(alpha = 0.8f),
            primaryPurple
        ),
        start = Offset(0f, 0f),
        end = Offset(300f, 300f)
    )

    LaunchedEffect(Unit) {
        delay(100)
        isLoaded = true
    }

    val density = LocalDensity.current
    val decorSize = with(density) { 150.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(accentPink.copy(alpha = 0.2f), Color.Transparent)
                    ),
                    radius = decorSize,
                    center = Offset(size.width * 0.1f, size.height * 0.1f)
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(accentBlue.copy(alpha = 0.15f), Color.Transparent)
                    ),
                    radius = decorSize * 1.2f,
                    center = Offset(size.width * 0.9f, size.height * 0.85f)
                )

                rotate(15f) {
                    drawRoundRect(
                        color = lightPurple.copy(alpha = 0.1f),
                        size = Size(decorSize * 0.8f, decorSize * 0.8f),
                        cornerRadius = CornerRadius(40f, 40f),
                        topLeft = Offset(size.width * 0.8f, -decorSize * 0.4f)
                    )
                }

                rotate(-10f) {
                    drawRoundRect(
                        color = accentPink.copy(alpha = 0.1f),
                        size = Size(decorSize, decorSize * 0.6f),
                        cornerRadius = CornerRadius(30f, 30f),
                        topLeft = Offset(-decorSize * 0.3f, size.height * 0.7f)
                    )
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "SoftMind",
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.White, accentPink.copy(alpha = 0.9f))
                    ),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp
                ),
                modifier = Modifier
                    .alpha(titleAlpha)
                    .padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .scale(cardScale)
                    .graphicsLayer {
                        shadowElevation = 8f
                        shape = RoundedCornerShape(28.dp)
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
                                    Color.White.copy(alpha = 0.3f),
                                    Color.White.copy(alpha = 0.1f)
                                )
                            )
                        )
                        .blur(radius = 0.5.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            accentPink.copy(alpha = 0.7f),
                                            primaryPurple.copy(alpha = 0.7f)
                                        )
                                    ),
                                    shape = CircleShape
                                )
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Headphones,
                                contentDescription = "Canal de Escuta",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Canal de Escuta",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Text(
                            text = "é um espaço seguro para você compartilhar seus sentimentos e receber apoio emocional. Nossos profissionais estão disponíveis 24 horas por dia para te ouvir e ajudar no que for preciso.",
                            color = Color.White.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(0.85f)
                    .height(60.dp)
                    .scale(buttonScale)
                    .graphicsLayer {
                        shadowElevation = 16f
                        shape = RoundedCornerShape(30.dp)
                        clip = true
                    }
                    .background(buttonGradient, RoundedCornerShape(30.dp))
                    .clickable { showContacts = !showContacts },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Chat,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = "Falar com alguém",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = showContacts,
                enter = fadeIn(animationSpec = tween(500)) + expandVertically(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
            ) {
                Card(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .graphicsLayer {
                            shadowElevation = 8f
                            shape = RoundedCornerShape(20.dp)
                            clip = true
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = 0.12f),
                                        Color.White.copy(alpha = 0.06f)
                                    )
                                )
                            )
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Contato",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            style = TextStyle(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color.White, Color(0xFFEC407A).copy(alpha = 0.9f))
                                )
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        ContactRow(
                            icon = Icons.Rounded.Chat,
                            label = "@softmind_apoio",
                            platform = "Instagram"
                        )

                        Divider(
                            color = Color.White.copy(alpha = 0.2f),
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        ContactRow(
                            icon = Icons.Rounded.Phone,
                            label = "(11) 99999-8888",
                            platform = "WhatsApp"
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun ContactRow(icon: ImageVector, label: String, platform: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = platform,
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .padding(end = 12.dp)
        )

        Column {
            Text(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = platform,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}
