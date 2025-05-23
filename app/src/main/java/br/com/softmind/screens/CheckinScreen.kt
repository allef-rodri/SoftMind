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
import br.com.softmind.ui.viewmodel.CheckinViewModel
import br.com.softmind.database.entity.Option
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CheckinScreen(
    navController: NavHostController,
    viewModel: CheckinViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var isLoaded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Observando os estados do ViewModel
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val emojiOptions by viewModel.emojiOptions.collectAsState()
    val selectedOption by viewModel.selectedOption.collectAsState()

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
            // Usar o título da pergunta atual do ViewModel
            Text(
                text = currentQuestion?.title ?: "Escolha o seu emoji de hoje!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Adicionar descrição se existir
//            currentQuestion?.description?.let { description ->
//                Text(
//                    text = description,
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.White.copy(alpha = 0.8f)
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//            }

            // Exibir os emojis organizados em grid de 3x2
            if (emojiOptions.isNotEmpty()) {
                EmojiOptionsGrid(
                    options = emojiOptions,
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        viewModel.selectOption(option)
                    }
                )
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
                        modifier = Modifier.alpha(if (selectedOption == null) 1f else 0f)
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
                            brush = if (selectedOption != null) buttonGradient else Brush.linearGradient(
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
                            enabled = selectedOption != null
                        ) {
                            scope.launch {
                                delay(100)
                                if (selectedOption != null) {
                                    // Salvar a resposta antes de navegar
                                    viewModel.saveAnswer {
                                        navController.navigate("${NavRoutes.DASHBOARD}/${selectedOption?.text ?: ""}")
                                    }
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
fun EmojiOptionsGrid(
    options: List<Option>,
    selectedOption: Option?,
    onOptionSelected: (Option) -> Unit
) {
    Column {
        // Divide as opções em linhas de 3
        val rows = options.chunked(3)

        rows.forEachIndexed { rowIndex, rowOptions ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = if (rowIndex > 0) 40.dp else 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowOptions.forEach { option ->
                    EmojiButton(
                        option = option,
                        isSelected = selectedOption?.optionId == option.optionId,
                        onSelect = { onOptionSelected(option) }
                    )
                }
            }
        }
    }
}

@Composable
fun EmojiButton(
    option: Option,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.5f else 1f,
        label = "emojiScale"
    )

    // Mapear o valor da opção para o recurso de desenho correspondente
    val resId = getEmojiResourceId(option.value!!)

    Icon(
        painter = painterResource(id = resId),
        contentDescription = option.text,
        modifier = Modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .size(68.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xFFE1BEE7) else Color.Transparent)
            .clickable { onSelect() }
            .padding(4.dp),
        tint = Color.Unspecified
    )
}

// Função auxiliar para mapear o valor da opção para o ID de recurso correspondente
fun getEmojiResourceId(value: String): Int {
    return when (value) {
        "happyface" -> br.com.softmind.R.drawable.happyface
        "tired" -> br.com.softmind.R.drawable.tired
        "sadface" -> br.com.softmind.R.drawable.sadface
        "scare" -> br.com.softmind.R.drawable.scare
        "scared" -> br.com.softmind.R.drawable.scared
        "angry" -> br.com.softmind.R.drawable.angry
        else -> br.com.softmind.R.drawable.happyface // Fallback padrão
    }
}