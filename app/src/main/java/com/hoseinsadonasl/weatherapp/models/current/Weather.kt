package com.hoseinsadonasl.weatherapp.models.current

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)