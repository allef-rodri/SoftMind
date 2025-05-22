package br.com.softmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import br.com.softmind.database.AppDatabase
import br.com.softmind.database.facade.SurveyDatabaseFacade
import br.com.softmind.ui.theme.SoftMindTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val appDatabase: AppDatabase by lazy { AppDatabase.getDatabase(applicationContext) }

    private val surveyDao by lazy { appDatabase.surveyDao() }

    val surveyDatabaseFacade by lazy { SurveyDatabaseFacade(surveyDao) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            if (!surveyDatabaseFacade.isDatabasePopulated()) {
                surveyDatabaseFacade.populateDatabase(lifecycleScope)
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
