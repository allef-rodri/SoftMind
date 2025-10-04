package br.com.softmind

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.database.AppDatabase
import br.com.softmind.database.facade.SurveyDatabaseFacade
import br.com.softmind.database.util.AuthManager
import br.com.softmind.ui.theme.SoftMindTheme
import br.com.softmind.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val appDatabase: AppDatabase by lazy { AppDatabase.getDatabase(applicationContext) }

    private val surveyDao by lazy { appDatabase.surveyDao() }

    val surveyDatabaseFacade by lazy { SurveyDatabaseFacade(surveyDao) }

    // Cria o ViewModel da atividade
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        RetrofitClient.init(this)

        // Preencher o DB se necessário
        lifecycleScope.launch {
            if (!surveyDatabaseFacade.isDatabasePopulated()) {
                surveyDatabaseFacade.populateDatabase(lifecycleScope)
            }
        }

        // 🔹 Chamada de login automática
        lifecycleScope.launch {
            val success = authViewModel.login()
            if (success) {
                Log.d("AUTH", "Login realizado com sucesso")
                Log.d("AUTH", "Token obtido: ${AuthManager.token}")
                Log.d("AUTH", "Expiração: ${AuthManager.expiresAt}")
            } else {
                Log.e("AUTH", "Falha ao realizar login")
            }
        }

        setContent {
            SoftMindTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MyAppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
