package com.example.weatherapppractice.data.remote

data class CurrentUnits(
    val interval: String,
    val is_day: String,
    val temperature_2m: String,
    val time: String,
    val weather_code: String
)