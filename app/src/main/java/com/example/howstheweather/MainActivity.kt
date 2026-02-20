package com.example.howstheweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.howstheweather.BuildConfig

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
                var screenState by remember { mutableStateOf<ScreenState>(ScreenState.Search) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
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

                                        println("API KEY VALUE: ${BuildConfig.WEATHER_API_KEY}")
                                        val call = api.getWeather(city, BuildConfig.WEATHER_API_KEY)
                                        call.enqueue(object : Callback<WeatherResponse> {
                                            override fun onResponse(
                                                call: Call<WeatherResponse>,
                                                response: Response<WeatherResponse>
                                            ) {
                                                println("RESPONSE CODE: ${response.code()}")  // add this
                                                println("RESPONSE ERROR: ${response.errorBody()?.string()}")

                                                if (response.isSuccessful) {
                                                    val weather = response.body()
                                                    temperature = weather?.main?.temp?.toString() ?: "N/A"
                                                    description = weather?.weather?.get(0)?.description ?: "N/A"
                                                } else {
                                                    temperature = "Error"
                                                    description = "City not found"
                                                }
                                                screenState = ScreenState.Results
                                            }

                                            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                                                temperature = "Error"
                                                description = "Network failure"
                                                screenState = ScreenState.Results
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
                                onSearchAgain = {
                                    city = ""
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