package com.venunalla.weatherapp.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(Date())
}

fun getNextDayDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar: Calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 1)
    val tomorrow: Date = calendar.time
    return dateFormat.format(tomorrow)
}

fun String.getDayFromDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(this, formatter)
    return dateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
}

fun String.getTimeFromDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(this, formatter)
    val outputFormatter = DateTimeFormatter.ofPattern("hha")
    return dateTime.format(outputFormatter)
}