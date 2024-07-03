package com.example.weatherapppractice.ui.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapppractice.R
import com.example.weatherapppractice.data.remote.WeatherApiResponse
import com.example.weatherapppractice.domain.weather.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourUpdateItems(modifier: Modifier = Modifier, hourlyState: () -> WeatherApiResponse?){
    Card(colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    )) {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = "Rainy Condition will continue for the rest of the day. Wind gusts are upto 22 km/h", style = MaterialTheme.typography.labelSmall)
            Divider(modifier = Modifier.fillMaxWidth())
            val hourlyData = hourlyState()?.hourly
            val timeList = hourlyData?.time ?: listOf()
            val temperatureList = hourlyData?.temperature_2m ?: listOf()
            val weatherType = hourlyData?.weather_code


            val currentDate = LocalDate.now()

            val filteredTimesAndTemps = timeList.zip(temperatureList)
                .filter { (timeString, _) ->
                    try {
                        val dateTime = LocalDateTime.parse(timeString)
                        dateTime.toLocalDate() == currentDate
                    } catch (e: DateTimeParseException) {
                        false
                    }
                }

            val formattedTimesAndTemps = filteredTimesAndTemps.map { (timeString, temp) ->
                try {
                    val dateTime = LocalDateTime.parse(timeString)
                    val currentHour = LocalDateTime.now().hour
                    val isCurrentHour = dateTime.hour == currentHour

                    val formattedTime = if (isCurrentHour) {
                        "Now"
                    } else {
                        dateTime.format(DateTimeFormatter.ofPattern("ha", Locale.getDefault())).lowercase()
                    }

                    formattedTime to temp
                } catch (e: DateTimeParseException) {
                    timeString to temp
                }
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
                items(formattedTimesAndTemps.size) { index ->
                    val (time, temperature) = formattedTimesAndTemps[index]
                    SingleItems(
                        modifier = Modifier,
                        time = time,
                        temperature = "${temperature}°"
                    ) { weatherType?.get(index) ?: 0 }
                }
            }

        }   
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewDailyUpdateItems(){
    Surface(modifier = Modifier.padding(20.dp)) {
//        HourUpdateItems(modifier = Modifier.padding(17.dp)) { weatherResponse() }
    }

}

@Composable
fun SingleItems(
    modifier: Modifier = Modifier,
    time: String = "Null",
    temperature: String = "Null",
    weatherCode: () -> Int
){
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        val weatherType =WeatherType.fromWMO(weatherCode())
        Text(text = time, style = MaterialTheme.typography.titleMedium)
        Icon(painter = painterResource(id = weatherType.iconRes), contentDescription ="Rainy" , modifier = Modifier.size(30.dp))
        Text(text = temperature)

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSingleItem(){
    Surface {
//        SingleItems(modifier = Modifier) { weatherType?.get(index) ?: 0 }
    }
}