package br.com.softmind.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val sentimentos = listOf(
    "Triste 😢",
    "Alegre 😄",
    "Cansado 😴",
    "Ansioso 😰",
    "Medo 😨",
    "Raiva 😡"
)

@Composable
fun CheckinScreen() {
    var selected by remember { mutableStateOf("") }
    var texto by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Como você está se sentindo hoje?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Column {
            sentimentos.forEach { sentimento ->
                Button(
                    onClick = { selected = sentimento },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selected == sentimento) Color(0xFF5EAAA8) else Color.LightGray
                    )
                ) {
                    Text(sentimento)
                }
            }
        }

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Deseja desabafar ou escrever algo?") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // salvar sentimento + texto
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5EAAA8))
        ) {
            Text("Registrar", color = Color.White)
        }
    }
}