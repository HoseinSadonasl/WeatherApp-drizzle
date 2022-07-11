package com.hoseinsadonasl.weatherapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.hoseinsadonasl.weatherapp.*
import com.kpstv.bindings.ConverterType
import retrofit2.http.Field

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