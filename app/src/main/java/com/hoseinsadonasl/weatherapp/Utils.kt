package com.hoseinsadonasl.weatherapp

import android.R
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun formatTime(timeMillis: Long): String {
    return SimpleDateFormat("MM/dd HH:mm")
        .format(timeMillis).toString()
}