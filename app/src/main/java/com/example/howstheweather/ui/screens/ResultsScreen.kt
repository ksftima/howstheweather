package com.example.howstheweather.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.howstheweather.ForecastItem
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.graphicsLayer

fun getWeatherEmoji(description: String): String {
    return when {
        description.contains("clear") -> "☀️"
        description.contains("few clouds") -> "🌤️"
        description.contains("cloud") -> "☁️"
        description.contains("rain") -> "🌧️"
        description.contains("drizzle") -> "🌦️"
        description.contains("thunder") -> "⛈️"
        description.contains("snow") -> "❄️"
        description.contains("mist") || description.contains("fog") -> "🌫️"
        else -> "🌡️"
    }
}

@Composable
fun FloatingEmoji(emoji: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -18f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatY"
    )

    Text(
        text = emoji,
        fontSize = 72.sp,
        modifier = Modifier.graphicsLayer { translationY = offsetY }
    )
}

@Composable
fun ResultsScreen(
    city: String,
    temperature: String,
    description: String,
    forecastItems: List<ForecastItem>,
    onSearchAgain: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = city.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${temperature.toDoubleOrNull()?.let { "%.0f".format(it) } ?: temperature}°C",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${getWeatherEmoji(description)}  ${description.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (forecastItems.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Hourly forecast",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                        ) {
                            forecastItems.forEach { item ->
                                val time = item.dt_txt.substring(11, 16)
                                val temp = "%.0f".format(item.main.temp)
                                val emoji = getWeatherEmoji(item.weather.firstOrNull()?.description ?: "")

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                ) {
                                    Text(
                                        text = time,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(text = emoji, fontSize = 24.sp)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "$temp°",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onSearchAgain) {
                Text("Search Again")
            }
        }
    }
}