package com.example.weatherapppractice.ui.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapppractice.R
import com.example.weatherapppractice.data.remote.WeatherApiResponse


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    weatherResponse: () -> WeatherApiResponse?,
    currentCity: () -> String?
){
    Box (){
        Image(painter = painterResource(id = R.drawable.cloudbg), contentDescription = null,
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(30.dp)) {

            CurrentWeather(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 45.dp),
                currentState ={ weatherResponse() }) { currentCity() }
            HourUpdateItems(modifier = Modifier.padding(17.dp)) { weatherResponse() }
            WeeklyUpdatesCard(modifier = Modifier.padding(17.dp)) { weatherResponse() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainApp(){
    Surface() {
//        MainApp(modifier = Modifier.fillMaxSize().padding(17.dp))
    }

}