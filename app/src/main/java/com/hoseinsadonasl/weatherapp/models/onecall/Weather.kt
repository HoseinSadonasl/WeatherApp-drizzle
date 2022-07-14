package com.hoseinsadonasl.weatherapp.models.onecall

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_tbl")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Double
)