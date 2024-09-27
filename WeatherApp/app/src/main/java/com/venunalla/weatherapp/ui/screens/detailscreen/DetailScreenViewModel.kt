package com.venunalla.weatherapp.ui.screens.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venunalla.weatherapp.model.WeatherData
import com.venunalla.weatherapp.repository.WeatherRepository
import com.venunalla.weatherapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    private val _weatherList = MutableStateFlow<List<WeatherData>?>(null)
    val weatherList: StateFlow<List<WeatherData>?> get() = _weatherList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

    private val _showErrorDialog = MutableStateFlow<String?>(null)
    val showErrorDialog get() = _showErrorDialog.asStateFlow()


    fun getWeatherDetailsByCity(city: String) {
        viewModelScope.launch {
            weatherRepository.getWeatherDetailByCity(city).onEach {
                when (it) {
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    is Result.Success -> {
                        _weatherList.value = it.data
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

    fun resetWeatherData() {
        _weatherList.value = null
    }

    fun reset() {
        _showErrorDialog.value = null
    }
}