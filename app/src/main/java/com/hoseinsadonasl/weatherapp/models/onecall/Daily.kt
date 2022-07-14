package com.hoseinsadonasl.weatherapp.models.onecall

data class Daily(
    val clouds: Double,
    val dew_poDouble: Double,
    val dt: Double,
    val feels_like: FeelsLike,
    val humidity: Double,
    val moon_phase: Double,
    val moonrise: Double,
    val moonset: Double,
    val pop: Double,
    val pressure: Double,
    val rain: Double,
    val sunrise: Double,
    val sunset: Double,
    val temp: Temp,
    val uvi: Double,
    val weather: List<WeatherXX>,
    val wind_deg: Double,
    val wind_gust: Double,
    val wind_speed: Double
)