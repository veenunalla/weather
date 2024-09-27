package com.venunalla.weatherapp.utils

/**
 * Sealed class for Result based on success , error and loading states
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}