package br.com.softmind

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.softmind.navigation.NavRoutes
import br.com.softmind.screens.*
import br.com.softmind.ui.viewmodel.CheckinViewModel
import br.com.softmind.ui.viewmodel.CheckinViewModelFactory

@Composable
fun MyAppNavigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(NavRoutes.HOME) {
            HomeScreen(navController)
        }
        composable(NavRoutes.CHECKIN) {
            // Obter a MainActivity do contexto local
            val mainActivity = LocalContext.current as MainActivity

            // Criar o ViewModel usando a Factory
            val viewModel: CheckinViewModel = viewModel(
                factory = CheckinViewModelFactory(mainActivity.surveyDatabaseFacade)
            )

            CheckinScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = "${NavRoutes.DASHBOARD}/{emoji}",
            arguments = listOf(navArgument("emoji") { defaultValue = "" })
        ) { backStackEntry ->
            val emoji = backStackEntry.arguments?.getString("emoji") ?: ""
            DashboardScreen(navController = navController, selectedEmoji = emoji)
        }
//        composable(NavRoutes.AVALIACAO) {
//            AutoAvaliacaoScreen(navController)
//        }
        composable(NavRoutes.SUPPORT) {
            SupportScreen(navController)
        }
        composable(NavRoutes.ALERTS) {
            AlertsScreen(navController)
        }
        composable(NavRoutes.WELLNESS) {
            WellnessScreen(navController)
        }

    }
}
