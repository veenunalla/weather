package com.venunalla.weatherapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venunalla.weatherapp.model.WeatherData
import com.venunalla.weatherapp.repository.DataStoreRepository
import com.venunalla.weatherapp.repository.WeatherRepository
import com.venunalla.weatherapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dataStoreRepository: DataStoreRepository
) :
    ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherData?>(null)
    val weatherData: StateFlow<WeatherData?> get() = _weatherData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

    private val _showErrorDialog = MutableStateFlow<String?>(null)
    val showErrorDialog get() = _showErrorDialog.asStateFlow()

    var savedCityAddress: Flow<String?> = dataStoreRepository.getSavedCityAddress()


    fun getWeatherByCity(city: String) {
        viewModelScope.launch {
            weatherRepository.getWeather(city).onEach {
                when (it) {
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    is Result.Success -> {
                        _weatherData.value = it.data
                        _isLoading.value = false
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        _showErrorDialog.value = it.exception.message ?: "unknown error"
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun saveCityName(city: String) =
        viewModelScope.launch {
            dataStoreRepository.saveCityAddress(city)
        }

    fun resetWeatherData() {
        _weatherData.value = null
    }

    fun reset() {
        _showErrorDialog.value = null
    }
}