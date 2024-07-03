package com.example.weatherapppractice.domain.weather

import com.example.weatherapppractice.data.remote.WeatherApiResponse

data class WeatherInfo(val weatherResponse: WeatherApiResponse?=null, val isLoading:Boolean = false, val error:String? = null , val CurrentCity:String? =null)
