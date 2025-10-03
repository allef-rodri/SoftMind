package br.com.softmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.database.AppDatabase
import br.com.softmind.database.facade.SurveyDatabaseFacade
import br.com.softmind.repository.AuthRepository
import br.com.softmind.ui.theme.SoftMindTheme
import kotlinx.coroutines.launch
import br.com.softmind.BuildConfig

class MainActivity : ComponentActivity() {
    private val appDatabase: AppDatabase by lazy { AppDatabase.getDatabase(applicationContext) }

    private val surveyDao by lazy { appDatabase.surveyDao() }

    val surveyDatabaseFacade by lazy { SurveyDatabaseFacade(surveyDao) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        RetrofitClient.init(this)

        var authReady by mutableStateOf(false)

        lifecycleScope.launch {
            try {
                val repo = AuthRepository(
                    RetrofitClient.authApi,
                    RetrofitClient.tokenStore()
                )
                repo.loginWithFixedToken(BuildConfig.FIXED_TOKEN)
            } catch (e: Exception) {
                // Em caso de falha, você pode logar ou tratar (exibir erro/retentar)
                // Para não travar o app, seguimos liberando a UI:
            } finally {
                authReady = true
            }
        }

        setContent {
            SoftMindTheme {
                if (!authReady) {
                    SplashLoading()
                } else {
                    val navController = rememberNavController()
                    MyAppNavigation(
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun SplashLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}