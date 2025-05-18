package br.com.softmind.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AlertsScreen() {
    var isLoaded by remember { mutableStateOf(false) }

    val accentBlue = Color(0xFF42A5F5)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4A148C),
            Color(0xFF6A1B9A),
            Color(0xFF8E24AA),
            Color(0xFF9C27B0)
        )
    )

    LaunchedEffect(Unit) {
        delay(300)
        isLoaded = true
    }

    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    data class Alert(
        val message: String,
        val category: String,
        val time: String,
        var read: Boolean = false
    )

    val alerts = remember {
        mutableStateListOf(
            Alert("Lembre-se de beber água regularmente durante o dia.", "Saúde Física", LocalTime.now().minusHours(1).format(formatter)),
            Alert("Evite o uso excessivo de redes sociais antes de dormir.", "Sono", LocalTime.now().minusHours(2).format(formatter)),
            Alert("Não se isole por longos períodos, busque interação social.", "Convivência", LocalTime.now().minusHours(3).format(formatter))
        )
    }

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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
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
                }

                Spacer(modifier = Modifier.height(8.dp))

                alerts.forEachIndexed { index, alert ->
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
                                    Text(
                                        text = "🕒 Hoje às ${alert.time}",
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
                                onClick = { alerts[index] = alerts[index].copy(read = true) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFFA000)
                                ),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(
                                    text = if (alert.read) "Lido" else "Marcar como lido",
                                    color = Color.White
                                )
                            }
                        }
                    }

                    if (index < alerts.size - 1) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AlertsScreenPreview() {
    AlertsScreen()
}