package com.hoseinsadonasl.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hoseinsadonasl.weatherapp.R
import com.hoseinsadonasl.weatherapp.network.ApiHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var weatherApi: ApiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}