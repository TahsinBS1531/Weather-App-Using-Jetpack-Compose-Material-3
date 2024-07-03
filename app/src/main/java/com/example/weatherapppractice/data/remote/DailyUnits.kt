package com.example.weatherapppractice.data.remote

data class DailyUnits(
    val rain_sum: String,
    val temperature_2m_max: String,
    val temperature_2m_min: String,
    val time: String,
    val weather_code: String
)