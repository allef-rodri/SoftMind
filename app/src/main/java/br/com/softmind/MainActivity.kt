package br.com.softmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import br.com.softmind.screens.CheckinScreen
import br.com.softmind.ui.theme.SoftMindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SoftMindTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CheckinScreen(
                    )
                }
            }
        }
    }
}
