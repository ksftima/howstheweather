package com.example.howstheweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import com.example.howstheweather.ui.theme.HowsTheWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            HowsTheWeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // weather UI starts here
                    var city by remember { mutableStateOf("") }

                    Column(
                        modifier =Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Row {
                            TextField(
                                value = city,
                                onValueChange = { city = it },
                                label = { Text("What place do you want to check the weather for?") }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {/*fetch weather*/}) {
                                Text("Search")
                            }
                        }
                    }
                    // end of weather ui
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HowsTheWeatherTheme {
        Greeting("Android")
    }
}