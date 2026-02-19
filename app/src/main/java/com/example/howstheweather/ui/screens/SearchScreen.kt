package com.example.howstheweather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    city: String,
    onCityChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = city,
                onValueChange = { onCityChange(it) },
                label = { Text("Enter city") },
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { onSearchClick() }) {
                Text("🔍") // emoji search button
            }
        }
    }
}

