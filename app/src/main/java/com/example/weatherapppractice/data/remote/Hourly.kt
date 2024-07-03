package com.example.weatherapppractice.data.remote

data class Hourly(
    val temperature_2m: List<Double>,
    val temperature_80m: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>,
    val wind_speed_10m: List<Double>
)