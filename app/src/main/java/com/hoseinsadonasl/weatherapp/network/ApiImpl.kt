package com.hoseinsadonasl.weatherapp.network

import android.location.Location
import com.google.gson.JsonObject
import com.hoseinsadonasl.weatherapp.models.Weather
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class ApiImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    @Named("location") private val location: ArrayList<Double>,
    @Named("exclude") private val exclude: String,
) : ApiHelper {
    override suspend fun getWeatherApi(): Response<Weather> {
        return weatherApi.getWeather(
            location[0].toString(),
            location[1].toString(),
            exclude
        )
    }
}