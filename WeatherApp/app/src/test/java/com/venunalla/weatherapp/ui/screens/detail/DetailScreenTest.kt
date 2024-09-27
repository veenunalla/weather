package com.venunalla.weatherapp.ui.screens.detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.venunalla.weatherapp.model.Clouds
import com.venunalla.weatherapp.model.Main
import com.venunalla.weatherapp.model.Rain
import com.venunalla.weatherapp.model.Sys
import com.venunalla.weatherapp.model.Weather
import com.venunalla.weatherapp.model.WeatherData
import com.venunalla.weatherapp.model.Wind
import com.venunalla.weatherapp.ui.screens.detailscreen.DetailScreen
import com.venunalla.weatherapp.ui.screens.detailscreen.ForecastSection
import com.venunalla.weatherapp.ui.screens.detailscreen.FutureForecastSection
import com.venunalla.weatherapp.ui.screens.detailscreen.HorizontalTodayWeather
import com.venunalla.weatherapp.ui.screens.detailscreen.VerticalFutureWeatherList
import com.venunalla.weatherapp.ui.theme.WeatherAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDetailScreenDisplaysCityName() {
        val cityName = "Charlotte"
        composeTestRule.setContent {
            WeatherAppTheme {
                DetailScreen(city = cityName)
            }
        }

        composeTestRule.onNodeWithText(cityName).assertExists()
    }

    @Test
    fun testDetailScreenDisplaysWeatherData() {
        val weatherData = WeatherData(
            dt = 1727395200,
            main = Main(285.65, 285.2, 285.06, 285.65, 991, 991, 986, 86, 0.59),
            weather = listOf(Weather(500, "Rain", "light rain", "10n")),
            clouds = Clouds(98),
            wind = Wind(5.93, 237, 11.63),
            visibility = 9364,
            pop = 1.0,
            rain = Rain(0.71),
            sys = Sys("n"),
            dt_txt = "2024-09-27 00:00:00"
        )

        composeTestRule.setContent {
            WeatherAppTheme {
                DetailScreen(city = "Charlotte")
            }
        }

        composeTestRule.onNodeWithText("Rain").assertExists()
        composeTestRule.onNodeWithText("light rain").assertExists()
    }

    @Test
    fun testForecastSectionDisplaysTodayForecast() {
        val weatherData = listOf(
            WeatherData(
                dt = 1727395200,
                main = Main(285.65, 285.2, 285.06, 285.65, 991, 991, 986, 86, 0.59),
                weather = listOf(Weather(500, "Rain", "light rain", "10n")),
                clouds = Clouds(98),
                wind = Wind(5.93, 237, 11.63),
                visibility = 9364,
                pop = 1.0,
                rain = Rain(0.71),
                sys = Sys("n"),
                dt_txt = "2024-09-27 00:00:00"
            )
        )

        composeTestRule.setContent {
            WeatherAppTheme {
                ForecastSection(weatherList = weatherData)
            }
        }

        composeTestRule.onNodeWithText("Today Forecast").assertExists()
        composeTestRule.onNodeWithText("Rain").assertExists()
    }

    @Test
    fun testFutureForecastSectionDisplaysNext5DaysForecast() {
        val weatherData = listOf(
            WeatherData(
                dt = 1727395200,
                main = Main(285.65, 285.2, 285.06, 285.65, 991, 991, 986, 86, 0.59),
                weather = listOf(Weather(500, "Rain", "light rain", "10n")),
                clouds = Clouds(98),
                wind = Wind(5.93, 237, 11.63),
                visibility = 9364,
                pop = 1.0,
                rain = Rain(0.71),
                sys = Sys("n"),
                dt_txt = "2024-09-27 12:00:00"
            )
        )

        composeTestRule.setContent {
            WeatherAppTheme {
                FutureForecastSection(weatherList = weatherData)
            }
        }

        composeTestRule.onNodeWithText("Next 5 days forecast").assertExists()
        composeTestRule.onNodeWithText("Rain").assertExists()
    }

    @Test
    fun testVerticalFutureWeatherListDisplaysWeatherData() {
        val weatherData = WeatherData(
            dt = 1727395200,
            main = Main(285.65, 285.2, 285.06, 285.65, 991, 991, 986, 86, 0.59),
            weather = listOf(Weather(500, "Rain", "light rain", "10n")),
            clouds = Clouds(98),
            wind = Wind(5.93, 237, 11.63),
            visibility = 9364,
            pop = 1.0,
            rain = Rain(0.71),
            sys = Sys("n"),
            dt_txt = "2024-09-27 12:00:00"
        )

        composeTestRule.setContent {
            WeatherAppTheme {
                VerticalFutureWeatherList(weatherData = weatherData)
            }
        }

        composeTestRule.onNodeWithText("Rain").assertExists()
        composeTestRule.onNodeWithText("light rain").assertExists()
    }

    @Test
    fun testHorizontalTodayWeatherDisplaysWeatherData() {
        val weatherData = WeatherData(
            dt = 1727395200,
            main = Main(285.65, 285.2, 285.06, 285.65, 991, 991, 986, 86, 0.59),
            weather = listOf(Weather(500, "Rain", "light rain", "10n")),
            clouds = Clouds(98),
            wind = Wind(5.93, 237, 11.63),
            visibility = 9364,
            pop = 1.0,
            rain = Rain(0.71),
            sys = Sys("n"),
            dt_txt = "2024-09-27 00:00:00"
        )

        composeTestRule.setContent {
            WeatherAppTheme {
                HorizontalTodayWeather(weatherData = weatherData)
            }
        }

        composeTestRule.onNodeWithText("Rain").assertExists()
        composeTestRule.onNodeWithText("light rain").assertExists()
    }
}