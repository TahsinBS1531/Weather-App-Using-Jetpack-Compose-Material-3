package com.example.weatherapppractice.ui.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapppractice.data.remote.Current
import com.example.weatherapppractice.data.remote.WeatherApiResponse
import com.example.weatherapppractice.domain.weather.WeatherType

@Composable
fun CurrentWeather(
    modifier: Modifier = Modifier,
    currentState: () -> WeatherApiResponse?,
    currentCity: () -> String?
){


    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)) {
        val weatherType = currentState()?.current?.weather_code?.let {
            WeatherType.fromWMO(it)
        }

        Text(text = "${currentCity()}", style = MaterialTheme.typography.titleLarge, color = Color.White)
        Text(text = "${currentState()?.current?.temperature_2m}°C", style = MaterialTheme.typography.displayMedium, color = Color.White)
        if (weatherType != null) {
            Text(text = weatherType.weatherDesc, style = MaterialTheme.typography.titleMedium, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCurrentWeather(){
    Surface {
//        CurrentWeather(modifier = Modifier.padding(horizontal = 20.dp, vertical = 45.dp)) { state().current }
    }
}