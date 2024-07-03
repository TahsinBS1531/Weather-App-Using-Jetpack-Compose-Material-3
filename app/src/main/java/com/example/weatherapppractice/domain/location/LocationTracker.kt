package com.example.weatherapppractice.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
    suspend fun getCurrentCity(location: Location):String?
}