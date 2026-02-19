package com.example.howstheweather.ui.screens

sealed class ScreenState {
    object Search : ScreenState()
    object Loading : ScreenState()
    object Results : ScreenState()
}