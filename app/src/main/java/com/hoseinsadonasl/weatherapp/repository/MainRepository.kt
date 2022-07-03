package com.hoseinsadonasl.weatherapp.repository

import android.location.Location
import android.util.Log
import androidx.lifecycle.asLiveData
import com.hoseinsadonasl.weatherapp.models.Weather
import com.hoseinsadonasl.weatherapp.network.WeatherApi
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.util.concurrent.Flow
import javax.inject.Inject
import javax.inject.Named

class MainRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    @Named("location") private val location: ArrayList<Double>,
    @Named("exclude") private val exclude: String
) {

    suspend fun getWeather() = flow {
        try {
            emit(
                weatherApi.getWeather(
                    lat = location[0].toString(),
                    lon = location[1].toString(),
                    exclude = exclude
                )
            )
            Log.e(
                "GetWeather==", "getWeather: ${
                    emit(
                        weatherApi.getWeather(
                            lat = location[0].toString(),
                            lon = location[1].toString(),
                            exclude = exclude
                        )
                    )
                }"
            )
        } catch (e: Exception) {
            Log.e("GetWeather", "getWeather: ${e.message}")
        }
    }

}