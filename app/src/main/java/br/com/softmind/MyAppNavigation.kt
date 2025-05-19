package br.com.softmind

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.softmind.screens.AlertsScreen
import br.com.softmind.screens.CheckinScreen
import br.com.softmind.screens.DashboardScreen
import br.com.softmind.screens.HomeScreen

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home", builder = {
        composable(route = "home") {
            HomeScreen(onStartClick = { navController.navigate("checkin") })
        }
        composable(route = "checkin") {
            CheckinScreen(onStartClick = { emoji ->
                navController.navigate("dashboard/$emoji")
            })
        }

        composable("alerta") {
            AlertsScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = "dashboard/{emoji}",
            arguments = listOf(navArgument("emoji") { type = NavType.StringType })
        ) { backStackEntry ->
            val emoji = backStackEntry.arguments?.getString("emoji") ?: ""
            val validEmojis = listOf("feliz", "cansado", "triste", "ansioso", "medo", "raiva")

            LaunchedEffect(emoji) {
                if (emoji.isEmpty() || emoji !in validEmojis) {
                    navController.navigate("checkin") {
                        popUpTo("checkin") { inclusive = true }
                    }
                }
            }
            
            DashboardScreen(
                selectedEmoji = emoji,
                onBackToHome = {
                    navController.navigate("checkin") {
                        popUpTo("home")
                    }
                },
                onShowAlerta = {
                    navController.navigate("alerta")
                }
            )
        }
    })
}