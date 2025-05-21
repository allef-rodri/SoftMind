package br.com.softmind

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.softmind.navigation.NavRoutes
import br.com.softmind.screens.*

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
            CheckinScreen(navController)
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
