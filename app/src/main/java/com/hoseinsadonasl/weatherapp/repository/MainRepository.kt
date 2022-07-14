package com.hoseinsadonasl.weatherapp.repository

import android.util.Log
import com.hoseinsadonasl.weatherapp.network.WeatherApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class MainRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    @Named("location") private val location: ArrayList<Double>,
    @Named("exclude") private val exclude: String
) {

    suspend fun getWeather(lat: String?, lon: String?) = flow {
        try {
            emit(
                weatherApi.getWeather(
                    lat = if (lat.isNullOrEmpty()) location[0].toString() else lat,
                    lon = if (lon.isNullOrEmpty()) location[0].toString() else lon,
                    exclude = exclude
                )
            )
        } catch (e: Exception) {
            Log.e("GetWeather", "getWeather: ${e.message}")
        }
    }

    suspend fun getCurrentWeatherByCityName(cityName: String) = flow {
        try {
            emit(weatherApi.getWeatherUsingCityName(cityName))
        } catch (e: Exception) {
            Log.e("GetWeather", "getWeather: ${e.message}")
        }
    }

}