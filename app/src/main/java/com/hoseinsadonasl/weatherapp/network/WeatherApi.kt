package com.hoseinsadonasl.weatherapp.network

import com.hoseinsadonasl.weatherapp.models.Weather
import com.hoseinsadonasl.weatherapp.other.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.*
import javax.inject.Named

interface WeatherApi {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("onecall?appid=$API_KEY")
    suspend fun getWeather(
        @Named("latitude") @Query("lat") lat: String,
        @Named("longitude") @Query("lon") lon: String,
        @Named("exclude") @Query("exclude") exclude: String
    ): Response<Weather>

}