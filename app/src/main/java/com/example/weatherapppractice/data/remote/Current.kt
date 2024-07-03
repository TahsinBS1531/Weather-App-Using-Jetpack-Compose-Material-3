package com.example.weatherapppractice.data.remote

data class Current(
    val interval: Int,
    val is_day: Int,
    val temperature_2m: Double,
    val time: String,
    val weather_code: Int
)