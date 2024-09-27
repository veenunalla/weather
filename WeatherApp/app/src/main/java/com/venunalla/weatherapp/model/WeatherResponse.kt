package com.venunalla.weatherapp.model

data class WeatherResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherData>,
    val city: City
)
