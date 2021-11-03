package com.hoseinsadonasl.weatherapp.other

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import javax.inject.Inject

class GetCurrentLocation @Inject constructor(
    var context: Context
) {
    private lateinit var locationManager: LocationManager

    @SuppressLint("MissingPermission")
    fun getLatLon(): ArrayList<Double> {
        val currentLocation = ArrayList<Double>()
        if (LocationUtility.hasPermission(context)) {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                5f
            ) { location ->
                Log.d("LOCATIONTAG", "onLocationChanged: ${location.latitude} ]")
                currentLocation.add(location.latitude)
                currentLocation.add(location.longitude)
            }
        }
        return currentLocation
    }
}