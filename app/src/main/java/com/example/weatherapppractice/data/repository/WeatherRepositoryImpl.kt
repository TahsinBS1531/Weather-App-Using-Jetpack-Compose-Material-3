package com.example.weatherapppractice.data.repository

import com.example.weatherapppractice.data.remote.WeatherApiInterface
import com.example.weatherapppractice.data.remote.WeatherApiResponse
import com.example.weatherapppractice.domain.repository.WeatherRepository
import com.example.weatherapppractice.domain.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor (private val weatherApi:WeatherApiInterface):WeatherRepository{
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherApiResponse> {
        return try {
            val response = weatherApi.getWeatherApi(lat, long)
            println(response.body())
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error("Api Request Failed", response.body())
            }
        } catch (e: Exception) {
            Resource.Error("Exception occurred: ${e.message}", null)
        }

    }

}