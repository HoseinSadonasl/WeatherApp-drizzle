package com.hoseinsadonasl.weatherapp.models.onecall

data class Hourly(
    val clouds: Double,
    val dew_poDouble: Double,
    val dt: Double,
    val feels_like: Double,
    val humidity: Double,
    val pop: Double,
    val pressure: Double,
    val rain: Rain,
    val temp: Double,
    val uvi: Double,
    val visibility: Double,
    val weather: List<WeatherXXX>,
    val wind_deg: Double,
    val wind_gust: Double,
    val wind_speed: Double
)