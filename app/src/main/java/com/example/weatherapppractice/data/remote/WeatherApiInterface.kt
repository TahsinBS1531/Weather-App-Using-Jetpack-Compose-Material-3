package com.example.weatherapppractice.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {

    @GET("v1/forecast")
    suspend fun getWeatherApi(@Query("latitude") lati:Double,
                      @Query("longitude") longi:Double,
                      @Query("current") current: String = "temperature_2m,is_day,weather_code",
                      @Query("hourly") hourly: String = "temperature_2m,weather_code,wind_speed_10m,temperature_80m",
                      @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min,rain_sum"
                      ):Response<WeatherApiResponse>
}