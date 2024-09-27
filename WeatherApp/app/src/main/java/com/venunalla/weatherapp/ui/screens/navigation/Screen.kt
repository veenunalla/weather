package com.venunalla.weatherapp.ui.screens.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("city_detail/{cityName}") {
        fun createRoute(cityName: String) = "city_detail/$cityName"
    }
}