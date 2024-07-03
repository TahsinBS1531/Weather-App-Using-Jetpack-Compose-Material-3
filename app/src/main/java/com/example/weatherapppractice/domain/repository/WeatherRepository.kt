package com.example.weatherapppractice.domain.repository

import com.example.weatherapppractice.data.remote.WeatherApiResponse
import com.example.weatherapppractice.domain.utils.Resource

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherApiResponse>
}