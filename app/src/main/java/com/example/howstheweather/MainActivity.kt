package com.example.howstheweather


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.howstheweather.ui.screens.*
import com.example.howstheweather.ui.theme.HowsTheWeatherTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(WeatherAPI::class.java)

        setContent {
            HowsTheWeatherTheme {

                var city by remember { mutableStateOf("") }
                var temperature by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }
                var forecastItems by remember { mutableStateOf<List<ForecastItem>>(emptyList()) }
                var screenState by remember { mutableStateOf<ScreenState>(ScreenState.Search) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Transparent
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (screenState) {
                            ScreenState.Search -> SearchScreen(
                                city = city,
                                onCityChange = { city = it },
                                onSearchClick = {
                                    if (city.isNotBlank()) {
                                        screenState = ScreenState.Loading

                                        var weatherDone = false
                                        var forecastDone = false

                                        fun checkIfBothDone() {
                                            if (weatherDone && forecastDone) {
                                                screenState = ScreenState.Results
                                            }
                                        }

                                        // Current weather call
                                        api.getWeather(city, BuildConfig.WEATHER_API_KEY)
                                            .enqueue(object : Callback<WeatherResponse> {
                                                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                                                    if (response.isSuccessful) {
                                                        val weather = response.body()
                                                        temperature = weather?.main?.temp?.toString() ?: "N/A"
                                                        description = weather?.weather?.get(0)?.description ?: "N/A"
                                                    } else {
                                                        temperature = "Error"
                                                        description = "City not found"
                                                    }
                                                    weatherDone = true
                                                    checkIfBothDone()
                                                }

                                                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                                                    temperature = "Error"
                                                    description = "Network failure"
                                                    weatherDone = true
                                                    checkIfBothDone()
                                                }
                                            })

                                        // Forecast call
                                        api.getForecast(city, BuildConfig.WEATHER_API_KEY)
                                            .enqueue(object : Callback<ForecastResponse> {
                                                override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                                                    if (response.isSuccessful) {
                                                        forecastItems = response.body()?.list?.take(8) ?: emptyList()
                                                    }
                                                    forecastDone = true
                                                    checkIfBothDone()
                                                }

                                                override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                                                    forecastDone = true
                                                    checkIfBothDone()
                                                }
                                            })
                                    }
                                }
                            )

                            ScreenState.Loading -> LoadingScreen()

                            ScreenState.Results -> ResultsScreen(
                                city = city,
                                temperature = temperature,
                                description = description,
                                forecastItems = forecastItems,
                                onSearchAgain = {
                                    city = ""
                                    forecastItems = emptyList()
                                    screenState = ScreenState.Search
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}