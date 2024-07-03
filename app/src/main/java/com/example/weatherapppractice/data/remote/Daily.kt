package com.example.weatherapppractice.data.remote

data class Daily(
    val rain_sum: List<Double>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>
)