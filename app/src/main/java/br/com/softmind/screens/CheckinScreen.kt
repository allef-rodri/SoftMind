package br.com.softmind.screens

import br.com.softmind.navigation.NavRoutes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.softmind.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CheckinScreen(navController: NavHostController) {

    var isLoaded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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

    LaunchedEffect(Unit) {
        delay(300)
        isLoaded = true
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val buttonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = tween(durationMillis = 100)
    )

    var selectedEmoji by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Escolha o seu emoji de hoje!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    EmojiButton(
                        name = "feliz",
                        resId = R.drawable.happyface,
                        selectedEmoji = selectedEmoji,
                        onSelect = { selectedEmoji = it }
                    )
                    EmojiButton(
                        name = "cansado",
                        resId = R.drawable.tired,
                        selectedEmoji = selectedEmoji,
                        onSelect = { selectedEmoji = it }
                    )
                    EmojiButton(
                        name = "triste",
                        resId = R.drawable.sadface,
                        selectedEmoji = selectedEmoji,
                        onSelect = { selectedEmoji = it }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    EmojiButton(
                        name = "ansioso",
                        resId = R.drawable.scare,
                        selectedEmoji = selectedEmoji,
                        onSelect = { selectedEmoji = it }
                    )
                    EmojiButton(
                        name = "medo",
                        resId = R.drawable.scared,
                        selectedEmoji = selectedEmoji,
                        onSelect = { selectedEmoji = it }
                    )
                    EmojiButton(
                        name = "raiva",
                        resId = R.drawable.angry,
                        selectedEmoji = selectedEmoji,
                        onSelect = { selectedEmoji = it }
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Por favor, selecione um emoji para continuar",
                        color = Color(0xFFFFA000),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        // Controle apenas a opacidade do texto
                        modifier = Modifier.alpha(if (selectedEmoji == null) 1f else 0f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(56.dp)
                        .scale(buttonScale)
                        .graphicsLayer {
                            shadowElevation = 16f
                            shape = RoundedCornerShape(28.dp)
                            clip = true
                        }
                        .background(
                            brush = if (selectedEmoji != null) buttonGradient else Brush.linearGradient(
                                colors = listOf(
                                    Color.Gray.copy(alpha = 0.5f),
                                    Color.Gray.copy(alpha = 0.3f),
                                    Color.Gray.copy(alpha = 0.5f)
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(300f, 300f)
                            ),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            enabled = selectedEmoji != null
                        ) {
                            scope.launch {
                                delay(100)
                                if (selectedEmoji != null) {
                                    navController.navigate("${NavRoutes.DASHBOARD}/$selectedEmoji")
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
                            text = "Vamos nessa!",
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
fun EmojiButton(
    name: String,
    resId: Int,
    selectedEmoji: String?,
    onSelect: (String) -> Unit
) {
    val isSelected = selectedEmoji == name
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.5f else 1f,
        label = "emojiScale"
    )

    Icon(
        painter = painterResource(id = resId),
        contentDescription = name,
        modifier = Modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .size(68.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xFFE1BEE7) else Color.Transparent)
            .clickable { onSelect(name) }
            .padding(4.dp),
        tint = Color.Unspecified
    )
}

