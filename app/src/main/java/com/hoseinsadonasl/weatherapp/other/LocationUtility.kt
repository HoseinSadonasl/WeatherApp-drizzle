package com.hoseinsadonasl.weatherapp.other

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object LocationUtility {

    fun hasPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

}