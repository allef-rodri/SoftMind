package br.com.softmind

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.softmind.screens.CheckinScreen
import br.com.softmind.screens.HomeScreen

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home", builder = {
        composable(route = "home") {
            HomeScreen(onStartClick = { navController.navigate("checkin") })
        }
        composable(route = "checkin") {
            CheckinScreen() }
        }
    )
}