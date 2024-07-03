package com.example.weatherapppractice

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapppractice.ui.theme.WeatherAppPracticeTheme
import com.example.weatherapppractice.ui.weather.MainApp
import com.example.weatherapppractice.ui.weather.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewmodel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {permission->
            if(permission[Manifest.permission.ACCESS_FINE_LOCATION]==true && permission[Manifest.permission.ACCESS_COARSE_LOCATION]==true){
                viewmodel.loadWeatherData()
            }else{
                viewmodel.loadWeatherDataWithDefaultLocation()
            }
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))

        println(viewmodel.state.weatherResponse)
        setContent {
            WeatherAppPracticeTheme {
                Box(modifier = Modifier.fillMaxSize()) {

                    if(viewmodel.state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }else if(viewmodel.state.error!=null){
                        viewmodel.state.error?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else{
                        val currentCity = viewmodel.state.CurrentCity
                        println("Current City: $currentCity")
                        viewmodel.state.weatherResponse?.let {
                            MainApp(modifier =  Modifier.padding(17.dp),weatherResponse ={it}) { currentCity }
                        }
                    }
                }

            }
        }
    }
}
