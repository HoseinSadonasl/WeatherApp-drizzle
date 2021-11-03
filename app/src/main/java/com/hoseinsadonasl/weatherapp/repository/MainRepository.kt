package com.hoseinsadonasl.weatherapp.repository

import com.hoseinsadonasl.weatherapp.models.Weather
import com.hoseinsadonasl.weatherapp.network.WeatherApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class MainRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    @Named("latitude") private val latitude: String,
    @Named("longitude") private val longitude: String,
    @Named("exclude") private val exclude: String,
) {

    suspend fun getWeather(): Response<Weather> =
        weatherApi.getWeather(
            lat = latitude,
            lon = longitude,
            exclude = exclude
        )
}