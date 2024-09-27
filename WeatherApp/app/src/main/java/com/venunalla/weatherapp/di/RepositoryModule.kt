package com.venunalla.weatherapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.venunalla.weatherapp.datasource.WeatherApiService
import com.venunalla.weatherapp.repository.DataStoreRepository
import com.venunalla.weatherapp.repository.DataStoreRepositoryImpl
import com.venunalla.weatherapp.repository.WeatherRepository
import com.venunalla.weatherapp.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideWeatherRepository(
        apiService: WeatherApiService,
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            apiService
        )
    }

    @Singleton
    @Provides
    fun providesDataStoreRepository(
        dataStore: DataStore<Preferences>,
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(
            dataStore
        )
    }
}