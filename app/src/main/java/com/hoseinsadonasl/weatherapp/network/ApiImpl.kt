package com.hoseinsadonasl.weatherapp.network

import com.hoseinsadonasl.weatherapp.models.current.CurrentWeather
import com.hoseinsadonasl.weatherapp.models.onecall.Weather
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class ApiImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    @Named("location") private val location: ArrayList<Double>,
    @Named("exclude") private val exclude: String,
) : ApiHelper {

    override suspend fun getWeatherApi(): Response<Weather> = weatherApi.getWeather(
        location[0].toString(),
        location[1].toString(),
        exclude
    )

    override suspend fun getWeatherApiByCityName(cityName: String): Response<CurrentWeather> =
        weatherApi.getWeatherUsingCityName(cityName)
}
