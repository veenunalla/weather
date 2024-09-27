package com.venunalla.weatherapp.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    companion object {
        val CITY_KEY = stringPreferencesKey("city")
    }

    override fun getSavedCityAddress(): Flow<String?> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                emit(emptyPreferences())
            }
        }.map {
            val city = it[CITY_KEY]
            city
        }
    }

    override suspend fun saveCityAddress(city: String) {
        dataStore.edit {
            it[CITY_KEY] = city
        }
    }
}
