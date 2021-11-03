package com.hoseinsadonasl.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hoseinsadonasl.weatherapp.R
import com.hoseinsadonasl.weatherapp.models.Weather
import com.hoseinsadonasl.weatherapp.network.ApiHelper
import com.hoseinsadonasl.weatherapp.network.WeatherApi
import com.hoseinsadonasl.weatherapp.other.Constants.API_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var weatherApi: ApiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}