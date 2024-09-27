package com.venunalla.weatherapp.ui.screens.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.venunalla.weatherapp.R
import com.venunalla.weatherapp.datasource.ApiURL.IMAGE_URL
import com.venunalla.weatherapp.model.WeatherData
import com.venunalla.weatherapp.ui.screens.component.CircularProgressDialog
import com.venunalla.weatherapp.ui.screens.component.ErrorDialog
import com.venunalla.weatherapp.ui.screens.navigation.Screen
import com.venunalla.weatherapp.utils.toCelcius

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val weatherData by viewModel.weatherData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()
    val searchText = remember { mutableStateOf("") }
    val address by viewModel.savedCityAddress.collectAsState("")
    LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.route == Screen.Home.route) {
                viewModel.resetWeatherData()
            }
        }
    }
    LaunchedEffect(address) {
        searchText.value = address as String
    }
    Scaffold(
        topBar = {
            Surface(shadowElevation = 5.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.search),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            val context = LocalContext.current

            TextField(
                value = searchText.value,
                onValueChange = {
                    searchText.value = it
                    viewModel.resetWeatherData()
                },
                label = {
                    Text(text = stringResource(R.string.search_by_city))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (searchText.value.isEmpty()) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_enter_city), Toast.LENGTH_LONG
                        ).show()
                    } else {
                        viewModel.getWeatherByCity(searchText.value)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.submit), style = MaterialTheme.typography.labelLarge)
            }
            CircularProgressDialog(isLoading, onDismiss = { })
            weatherData?.let {
                // save city name on successful search
                viewModel.saveCityName(city = searchText.value)

                WeatherItem(
                    it,
                    paddingValues = innerPadding,
                    searchText = searchText.value,
                    navController
                )
            }
            showErrorDialog?.let {
                ErrorDialog(onDismiss = { viewModel.reset() }, errorMessage = it)
            }
        }
    }
}

@Composable
fun WeatherItem(
    data: WeatherData,
    paddingValues: PaddingValues,
    searchText: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.splash_background), //Card background color
            contentColor = Color.White  //Card content color,e.g.text
        )

    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(R.string.now), style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            )
            Text(
                text = searchText, style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            )
            Text(
                text = String.format(
                    stringResource(R.string._2f_c), data.main.temp.toCelcius()
                ), style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            AsyncImage(
                model = stringResource(R.string._2x_png, IMAGE_URL, data.weather[0].icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                alignment = Alignment.Center
            )

            Text(
                text = data.weather[0].main, style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = data.weather[0].description, style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 8.dp)
            ) {
                WeatherBox(R.string.feels_like, data.main.feels_like)
                WeatherBox(R.string.max, data.main.temp_max)
                WeatherBox(R.string.min, data.main.temp_min)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp)
                        .background(
                            colorResource(R.color.box_background),
                            shape = RoundedCornerShape(5)
                        )
                        .shadow(1.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format(
                            stringResource(R.string.wind_speed), data.wind.speed.toString()
                        ),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp)
                        .background(
                            colorResource(R.color.box_background),
                            shape = RoundedCornerShape(5)
                        )
                        .shadow(1.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format(
                            stringResource(R.string.humidity), data.main.humidity
                        ),
                        color = Color.White, style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    Button(
        onClick = {
            navController.navigate(Screen.Detail.createRoute(searchText))
        },
        Modifier
            .padding(10.dp)
            .fillMaxWidth(),
    ) {
        Text(
            stringResource(R.string.click_for_next_5_days_weather),
            style = MaterialTheme.typography.labelLarge,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun RowScope.WeatherBox(label: Int, weather: Double) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(8.dp)
            .background(colorResource(R.color.box_background), shape = RoundedCornerShape(5))
            .shadow(1.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = String.format(
                stringResource(label), weather.toCelcius()
            ), color = Color.White, style = MaterialTheme.typography.bodyLarge
        )
    }
}
