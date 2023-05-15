package com.lizw.ui_demos.customview.weatherview

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lizw.ui_demos.R

class WeatherActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        val weatherView = findViewById<WeatherView>(R.id.weather_view)
        findViewById<WeatherHScrollView>(R.id.scrollView_h).apply {
            setWeatherView(weatherView)
        }
    }
}