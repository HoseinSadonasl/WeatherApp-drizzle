package com.hoseinsadonasl.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat

object LocationUtility {

    fun hasPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

}

object Constants {

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    const val API_KEY = "Your api key"

    const val REQUEST_CODE_LOCATION_PERMISSION = 0

}

@SuppressLint("SimpleDateFormat")
fun formatTime(timeMillis: Long): String {
    return SimpleDateFormat("MM/dd HH:mm")
        .format(timeMillis * 1000).toString()
}

@SuppressLint("SimpleDateFormat")
fun formatDailyAdapterTime(timeMillis: Long): String {
    return SimpleDateFormat("EEE, MMM d").format(timeMillis * 1000).toString()
}
