package br.com.softmind.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.softmind.R

@Composable
fun CheckinScreen() {

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Green)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            Row {
                Text(text = "Escolha o seu emoji de hoje!", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
            Row {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.happyface),
                            contentDescription = "Alegre",
                            modifier = Modifier.size(68.dp),
                            tint = Color.Unspecified
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.tired),
                            contentDescription = "Cansado",
                            modifier = Modifier.size(68.dp),
                            tint = Color.Unspecified
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.sadface),
                            contentDescription = "Triste",
                            modifier = Modifier.size(68.dp),
                            tint = Color.Unspecified
                        )

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 40.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.scare),
                            contentDescription = "Ansioso",
                            modifier = Modifier.size(68.dp),
                            tint = Color.Unspecified
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.scared),
                            contentDescription = "Medo",
                            modifier = Modifier.size(68.dp),
                            tint = Color.Unspecified
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.angry),
                            contentDescription = "Raiva",
                            modifier = Modifier.size(68.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
            Row {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(bottom = 50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007AFF)
                    )
                ) {
                    Text(text = "Enviar")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckinScreenPreview() {
    CheckinScreen()
}