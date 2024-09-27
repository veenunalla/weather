package com.venunalla.weatherapp.utils

import com.venunalla.weatherapp.utils.Constants.KELVIN_DEGRESS


fun Double.toCelcius(): Int {
    return (this - KELVIN_DEGRESS).toInt()
}
