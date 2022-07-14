package com.hoseinsadonasl.weatherapp.network

import com.hoseinsadonasl.weatherapp.models.current.CurrentWeather
import com.hoseinsadonasl.weatherapp.models.onecall.Weather
import retrofit2.Response

interface ApiHelper {
    suspend fun getWeatherApi() :  Response<Weather>
    suspend fun getWeatherApiByCityName(cityName: String) :  Response<CurrentWeather>
}