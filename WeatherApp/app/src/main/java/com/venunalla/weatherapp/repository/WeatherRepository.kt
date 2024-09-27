package com.venunalla.weatherapp.repository

import com.venunalla.weatherapp.model.WeatherData
import com.venunalla.weatherapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(city: String): Flow<Result<WeatherData>>
    suspend fun getWeatherDetailByCity(city: String): Flow<Result<List<WeatherData>>>
}