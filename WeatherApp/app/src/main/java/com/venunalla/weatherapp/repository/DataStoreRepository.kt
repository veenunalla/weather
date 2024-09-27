package com.venunalla.weatherapp.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun getSavedCityAddress(): Flow<String?>

    suspend fun saveCityAddress(city: String)
}