package com.venunalla.weatherapp.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.venunalla.weatherapp.ui.screens.detailscreen.DetailScreen
import com.venunalla.weatherapp.ui.screens.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("cityName") { type = NavType.StringType })
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName")
            DetailScreen(cityName)
        }
    }
}