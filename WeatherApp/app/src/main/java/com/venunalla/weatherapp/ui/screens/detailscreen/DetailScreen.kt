package com.venunalla.weatherapp.ui.screens.detailscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.venunalla.weatherapp.R
import com.venunalla.weatherapp.datasource.ApiURL.IMAGE_URL
import com.venunalla.weatherapp.model.WeatherData
import com.venunalla.weatherapp.ui.screens.component.CircularProgressDialog
import com.venunalla.weatherapp.utils.getCurrentDate
import com.venunalla.weatherapp.utils.getDayFromDate
import com.venunalla.weatherapp.utils.getNextDayDate
import com.venunalla.weatherapp.utils.getTimeFromDate
import com.venunalla.weatherapp.utils.toCelcius

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(city: String?) {
    val viewModel = hiltViewModel<DetailScreenViewModel>()
    val weatherData by viewModel.weatherList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()
    LaunchedEffect(Unit) {
        city?.let {
            viewModel.getWeatherDetailsByCity(city)
        }
    }
    Scaffold {
        CircularProgressDialog(isLoading, onDismiss = { })
        val gradient = Brush.verticalGradient(
            colors = listOf(
                colorResource(R.color.splash_background),
                colorResource(R.color.splash_background)
            )
        )
        weatherData?.let { weatherList ->
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .background(brush = gradient)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            city ?: "",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                        weatherList.firstOrNull()?.let { currentData ->
                            Text(
                                String.format(
                                    stringResource(R.string._2f_c),
                                    currentData.main.temp.toCelcius()
                                ),
                                style = MaterialTheme.typography.displayLarge,
                                fontSize = 100.sp
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                AsyncImage(
                                    model = stringResource(
                                        R.string._2x_png,
                                        IMAGE_URL,
                                        currentData.weather[0].icon
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.padding(4.dp)
                                )
                                Text(
                                    currentData.weather.first().main,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.White,
                                    modifier = Modifier.padding(6.dp)
                                )
                            }
                            Text(
                                String.format(
                                    stringResource(R.string.max_min),
                                    currentData.main.temp_max.toCelcius(),
                                    currentData.main.temp_min.toCelcius()
                                ),
                                style = MaterialTheme.typography.headlineMedium,
                                color = colorResource(R.color.gray),
                                fontSize = 20.sp
                            )
                        }
                        ForecastSection(weatherList)
                        FutureForecastSection(weatherList)
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastSection(weatherList: List<WeatherData>?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.splash_background),
            contentColor = Color.White  //Card content color,e.g.text
        )
    ) {
        Text(
            "Today Forecast",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.displayMedium
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            thickness = 1.dp,
            color = colorResource(R.color.box_background)
        )
        val todayWeatherList =
            weatherList?.filter {
                it.dt_txt.contains(getCurrentDate()) || it.dt_txt.contains(
                    getNextDayDate()
                )
            } ?: emptyList()
        LazyRow {
            items(todayWeatherList) {
                HorizontalTodayWeather(it)
            }
        }
    }
}

@Composable
fun FutureForecastSection(weatherList: List<WeatherData>) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.splash_background),
            contentColor = Color.White  //Card content color,e.g.text
        )
    ) {
        Text(
            "Next 5 days forecast",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.displayMedium
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            thickness = 1.dp,
            color = colorResource(R.color.box_background)
        )
        val futureDayList = weatherList.filter { it.dt_txt.contains("12:00:00") }
        LazyColumn {
            items(futureDayList) {
                VerticalFutureWeatherList(it)
            }
        }
    }
}


@Composable
fun VerticalFutureWeatherList(weatherData: WeatherData) {
    Card(
        // in the below line, we are adding
        // padding from our all sides.
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.splash_background),
            contentColor = Color.White  //Card content color,e.g.text
        )
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = weatherData.dt_txt.getDayFromDate(),
                color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
            AsyncImage(
                model = stringResource(
                    R.string._2x_png,
                    IMAGE_URL,
                    weatherData.weather[0].icon
                ),
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                String.format(
                    stringResource(R.string._2f_c),
                    weatherData.main.temp.toCelcius()
                ),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                modifier = Modifier
                    .padding(6.dp)
                    .weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                String.format(
                    stringResource(R.string.max_min),
                    weatherData.main.temp_max.toCelcius(),
                    weatherData.main.temp_min.toCelcius()
                ),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                modifier = Modifier
                    .padding(6.dp)
                    .weight(1f),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            thickness = 3.dp,
            color = colorResource(R.color.white)
        )
    }
}


@Composable
fun HorizontalTodayWeather(weatherData: WeatherData) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.splash_background),
            contentColor = Color.White  //Card content color,e.g.text
        )
    )
    {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                weatherData.dt_txt.getTimeFromDate(),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                modifier = Modifier
                    .padding(6.dp)
                    .weight(1f)
            )
            AsyncImage(
                model = stringResource(
                    R.string._2x_png,
                    IMAGE_URL,
                    weatherData.weather[0].icon
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .weight(2f)
            )
            Text(
                String.format(
                    stringResource(R.string._2f_c), weatherData.main.temp.toCelcius()
                ),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                modifier = Modifier
                    .padding(6.dp)
                    .weight(1f)
            )
        }
    }
}
