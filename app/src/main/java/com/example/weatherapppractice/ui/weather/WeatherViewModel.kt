package com.example.weatherapppractice.ui.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapppractice.data.repository.WeatherRepositoryImpl
import com.example.weatherapppractice.domain.location.LocationTracker
import com.example.weatherapppractice.domain.utils.Resource
import com.example.weatherapppractice.domain.weather.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepositoryImpl,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherInfo())
        private set

    fun loadWeatherData() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            locationTracker.getCurrentLocation()?.let { location ->
                println("latitude: ${location.latitude}")
                println("longitude: ${location.longitude}")

                val currentCity = locationTracker.getCurrentCity(location)

                when (val result = repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherResponse = result.data,
                            isLoading = false,
                            error = null,
                            CurrentCity = currentCity
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherResponse = null,
                            isLoading = false,
                            error = "There is an error on the network. Please check your internet connection."
                        )
                    }
                }

            } ?: run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to have an internet connection."
                )
            }
        }
    }

    fun loadWeatherDataWithDefaultLocation() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            when (val result = repository.getWeatherData(lat = 23.777176, long = 90.399452)) {
                is Resource.Success -> {
                    state = state.copy(
                        weatherResponse = result.data,
                        isLoading = false,
                        error = null,
                        CurrentCity = "Dhaka"
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        weatherResponse = null,
                        isLoading = false,
                        error = "Couldn't retrieve the default location. Please check your internet connection."
                    )
                }
            }
        }
    }
}
