package com.hoseinsadonasl.weatherapp.network

import com.google.gson.JsonObject
import com.hoseinsadonasl.weatherapp.models.Weather
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class ApiImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    @Named("latitude") private val lat: String,
    @Named("longitude") private val lon: String,
    @Named("exclude") private val exclude: String,
    @Named("appid") private val appId: String
) : ApiHelper {
    override suspend fun getWeatherApi():  Response<Weather> {
       return weatherApi.getWeather(lat, lon, exclude, appId)
    }
}