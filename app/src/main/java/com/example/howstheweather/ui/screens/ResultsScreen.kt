package com.example.howstheweather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultsScreen(
    city: String,
    temperature: String,
    description: String,
    onSearchAgain: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(text = city, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "$temperature°C", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description.replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onSearchAgain) {
                Text("Search Again")
            }
        }
    }
}