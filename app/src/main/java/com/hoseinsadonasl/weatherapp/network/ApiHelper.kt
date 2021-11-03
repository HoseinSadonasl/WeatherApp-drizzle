package com.hoseinsadonasl.weatherapp.network

import com.hoseinsadonasl.weatherapp.models.Weather
import retrofit2.Response

interface ApiHelper {
    suspend fun getWeatherApi() :  Response<Weather>
}