package com.hoseinsadonasl.weatherapp.network

import com.hoseinsadonasl.weatherapp.models.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Named

interface WeatherApi {

    @GET("onecall")
    suspend fun getWeather(
        @Named("latitude") @Query("lat") lat: String,
        @Named("longitude") @Query("lon") lon: String,
        @Named("exclude") @Query("exclude") exclude: String,
        @Named("appid") @Query("appid") appid: String
    ): Response<Weather>

}