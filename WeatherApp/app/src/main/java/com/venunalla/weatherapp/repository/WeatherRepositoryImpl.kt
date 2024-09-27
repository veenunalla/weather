package com.venunalla.weatherapp.repository

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.venunalla.weatherapp.datasource.ApiURL.API_KEY
import com.venunalla.weatherapp.datasource.WeatherApiService
import com.venunalla.weatherapp.model.WeatherData
import com.venunalla.weatherapp.model.WeatherResponse
import com.venunalla.weatherapp.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApiService: WeatherApiService) :
    WeatherRepository {
    override suspend fun getWeather(city: String): Flow<Result<WeatherData>> = flow {
        emit(Result.Loading)
        try {
            val searchResult = weatherApiService.getWeather(city = city, apiKey = API_KEY)
            val weatherData = getCurrentDateWeather(searchResult)
            weatherData?.let {
                emit(Result.Success(weatherData))
            }
            if (weatherData == null) {
                emit(Result.Error(Exception("Weather Data Not Available")))
            }
        } catch (exception: Exception) {
            val errorMessage = parseError(exception)
            emit(Result.Error(Exception(errorMessage)))
        }
    }

    override suspend fun getWeatherDetailByCity(city: String): Flow<Result<List<WeatherData>>> =
        flow {
            emit(Result.Loading)
            try {
                val searchResult = weatherApiService.getWeather(city = city, apiKey = API_KEY)
                val weatherList = getWeatherForAWeek(searchResult)
                weatherList?.let {
                    emit(Result.Success(weatherList))
                }
                if (weatherList.isNullOrEmpty()) {
                    emit(Result.Error(Exception("Weather Data Not Available")))
                }
            } catch (exception: Exception) {
                val errorMessage = parseError(exception)
                emit(Result.Error(Exception(errorMessage)))
            }
        }

    private fun parseError(exception: Exception): String {
        if (exception is HttpException) {
            val errorResponse = exception.response()?.errorBody()?.string()
            if (errorResponse != null) {
                val errorJson = JSONObject(errorResponse)
                if (errorJson.getString("cod") == "404") {
                    return errorJson.getString("message").capitalize(Locale.current)
                } else {
                    return "Unknown error"
                }
            } else {
                return "Unknown error"
            }
        } else {
            return exception.message?.capitalize(Locale.current) ?: "Unknown error"
        }
    }

    private fun getCurrentDateWeather(weatherResponse: WeatherResponse?): WeatherData? {
        val currentTime = LocalDateTime.now(ZoneOffset.UTC)
        return weatherResponse?.list?.find {
            val itemTime = LocalDateTime.ofEpochSecond(it.dt, 0, ZoneOffset.UTC)
            itemTime.isEqual(currentTime) || itemTime.isAfter(currentTime)
        }
    }

    private fun getWeatherForAWeek(weatherResponse: WeatherResponse?): List<WeatherData>? {
        val currentTime = LocalDateTime.now(ZoneOffset.UTC)
        val weatherDataList = weatherResponse?.list?.filter {
            val itemTime = LocalDateTime.ofEpochSecond(it.dt, 0, ZoneOffset.UTC)
            itemTime.isEqual(currentTime) || itemTime.isAfter(currentTime)
        }
        return weatherDataList
    }
}