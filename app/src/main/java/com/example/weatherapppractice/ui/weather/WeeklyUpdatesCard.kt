package com.example.weatherapppractice.ui.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapppractice.R
import com.example.weatherapppractice.data.remote.WeatherApiResponse
import com.example.weatherapppractice.domain.weather.DailyWeather
import com.example.weatherapppractice.domain.weather.WeatherType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyUpdatesCard(modifier: Modifier = Modifier, weeklyUpdate: () -> WeatherApiResponse?){
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column(modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.DateRange, contentDescription ="Date" )
                Text(text = "Daily Forecast".uppercase())
            }
            val dailyData = weeklyUpdate()?.daily
            if (dailyData != null) {
                dailyData.weather_code
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                if (dailyData != null) {
                    items(dailyData.time.size){
                        WeeklyUpdatesSingleItem(modifier = Modifier,day ={dailyData.time[it]},minTemp ={dailyData.temperature_2m_min[it]},highTemp ={ dailyData.temperature_2m_max[it] }) { dailyData.weather_code[it] }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWeeklyUpdates(){
    Surface(modifier = Modifier.padding(17.dp)) {
//        WeeklyUpdatesCard(modifier = Modifier.padding(17.dp)) { weatherResponse() }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyUpdatesSingleItem(
    modifier: Modifier = Modifier,
    day: () -> String,
    minTemp: () -> Double,
    highTemp: () -> Double,
    weatherCode: () -> Int
){
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()) {

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val date = LocalDate.parse(day(), dateFormatter)
        val dayOfWeek = date.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())

        val weatherType = WeatherType.fromWMO(weatherCode())

        Text(modifier = Modifier.width(100.dp),text = dayOfWeek, style = MaterialTheme.typography.titleMedium)
        Icon(painter = painterResource(id = weatherType.iconRes), contentDescription ="Rain", modifier = Modifier.size(30.dp) )
        TemperatureProgressBar(temperature = minTemp().toFloat(), maxTemperature = highTemp().toFloat())
    }
}


@Preview
@Composable
fun PreviewWeeklyUpdatesSingleItem(){
    Surface {
//        WeeklyUpdatesSingleItem(modifier = Modifier,
//            { dailyData.time[it] },
//            { dailyData.temperature_2m_min[it] }
//        ) { dailyData.temperature_2m_max[it] }
    }
}



@Composable
fun TemperatureProgressBar(temperature: Float, maxTemperature: Float) {
    val progress = (temperature / maxTemperature).coerceIn(0f, 1f)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$temperature°C", style = MaterialTheme.typography.titleMedium)
        LinearProgressIndicator(progress = progress, modifier = Modifier.width(100.dp), trackColor = Color.White)
        Text(text = "$maxTemperature°C", style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun TemperatureProgressBarPreview() {
    TemperatureProgressBar(temperature = 28f, maxTemperature = 50f)
}