package br.com.softmind.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.softmind.model.AlertResponse
import br.com.softmind.ui.viewmodel.AlertsUiState
import br.com.softmind.ui.viewmodel.AlertsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AlertsScreen(
    navController: NavHostController,
    viewModel: AlertsViewModel = viewModel()
) {
    // Controla a animação de entrada da tela
    var isLoaded by remember { mutableStateOf(false) }

    // Observa o estado exposto pelo ViewModel
    val uiState by viewModel.uiState.collectAsState()

    val accentBlue = Color(0xFF42A5F5)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4A148C),
            Color(0xFF6A1B9A),
            Color(0xFF8E24AA),
            Color(0xFF9C27B0)
        )
    )

    // Delay para animar a entrada da lista
    LaunchedEffect(Unit) {
        delay(300)
        isLoaded = true
    }

    // Formatter para exibir somente a hora no alerta
    val hourFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Cabeçalho
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = null,
                        tint = accentBlue,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Alertas Recentes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = accentBlue
                        )
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                // Conteúdo baseado no estado da lista de alertas
                when (val state = uiState) {
                    is AlertsUiState.Loading -> {
                        // Estado de carregamento: mostra um indicador
                        if (isLoaded) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp)
                                    .graphicsLayer {
                                        shadowElevation = 8f
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White.copy(alpha = 0.15f)
                                ),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(48.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                    Text(
                                        text = "Carregando alertas...",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                    is AlertsUiState.Success -> {
                        val alerts = state.alerts
                        if (alerts.isEmpty()) {
                            // Nenhum alerta para exibir
                            Text(
                                text = "Você não tem alertas no momento.",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        } else {
                            alerts.forEachIndexed { index, alert ->
                                AlertCard(
                                    alert = alert,
                                    hourFormatter = hourFormatter,
                                    onMarkAsRead = { viewModel.markAsRead(alert.id) }
                                )
                                if (index < alerts.size - 1) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                    is AlertsUiState.Error -> {
                        // Mensagem de erro com opção de tentar novamente
                        if (isLoaded) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp)
                                    .graphicsLayer {
                                        shadowElevation = 8f
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White.copy(alpha = 0.15f)
                                ),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
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
                                        text = state.message,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                    Button(
                                        onClick = { viewModel.refresh() },
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

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * Representa um único cartão de alerta na lista. Desacoplado para tornar o código
 * principal da tela mais legível.
 *
 * @param alert dados do alerta a ser exibido.
 * @param hourFormatter formatador para converter a data ISO em horário local.
 * @param onMarkAsRead callback chamado quando o usuário pressiona o botão para marcar como lido.
 */
@Composable
private fun AlertCard(
    alert: AlertResponse,
    hourFormatter: DateTimeFormatter,
    onMarkAsRead: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .graphicsLayer {
                shadowElevation = 4f
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF9C4).copy(alpha = 0.25f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            Color(0xFFFFA000).copy(alpha = 0.2f),
                            CircleShape
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.sweepGradient(
                                listOf(
                                    Color(0xFFFFA000).copy(alpha = 0.7f),
                                    Color(0xFFFFA000).copy(alpha = 0.3f),
                                    Color(0xFFFFA000).copy(alpha = 0.7f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = "Alerta",
                        tint = Color(0xFFFFA000),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = alert.message,
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    // Converte a string ISO de data para horário local
                    val time = try {
                        Instant.parse(alert.createdAt)
                            .atZone(ZoneId.systemDefault())
                            .toLocalTime()
                            .format(hourFormatter)
                    } catch (_: Exception) {
                        "--:--"
                    }
                    Text(
                        text = "🕒 Hoje às $time",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = alert.category,
                        color = Color.Yellow,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onMarkAsRead,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA000)
                ),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = if (alert.isRead) "Lido" else "Marcar como lido",
                    color = Color.White
                )
            }
        }
    }
}
