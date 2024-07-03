package com.example.weatherapppractice.data.remote

data class HourlyUnits(
    val temperature_2m: String,
    val temperature_80m: String,
    val time: String,
    val weather_code: String,
    val wind_speed_10m: String
)