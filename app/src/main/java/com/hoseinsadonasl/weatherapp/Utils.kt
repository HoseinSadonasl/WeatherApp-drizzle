package com.hoseinsadonasl.weatherapp

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun formatTime(timeMillis: Long): String {
    return SimpleDateFormat("MM/dd HH:mm")
        .format(timeMillis * 1000).toString()
}

@SuppressLint("SimpleDateFormat")
fun formatDailyAdapterTime(timeMillis: Long): String {
    return SimpleDateFormat("EEE, MMM d").format(timeMillis * 1000).toString()

}