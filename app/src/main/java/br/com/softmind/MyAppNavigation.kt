package br.com.softmind

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.softmind.navigation.NavRoutes
import br.com.softmind.screens.*
import br.com.softmind.ui.viewmodel.CheckinViewModel
import br.com.softmind.ui.viewmodel.CheckinViewModelFactory
import br.com.softmind.ui.viewmodel.HomeViewModel
import br.com.softmind.ui.viewmodel.HomeViewModelFactory

@Composable
fun MyAppNavigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(NavRoutes.HOME) {
            // Obter a MainActivity usando LocalActivity
            val mainActivity = LocalActivity.current as MainActivity

            // Criar o HomeViewModel usando a HomeViewModelFactory
            val viewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(mainActivity.surveyDatabaseFacade)
            )

            HomeScreen(navController = navController, viewModel = viewModel)
        }

        composable(NavRoutes.CHECKIN) {
            // Obter a MainActivity usando LocalActivity
            val mainActivity = LocalActivity.current as MainActivity

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
