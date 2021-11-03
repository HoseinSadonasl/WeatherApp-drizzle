package com.hoseinsadonasl.weatherapp.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hoseinsadonasl.weatherapp.R
import com.hoseinsadonasl.weatherapp.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.hoseinsadonasl.weatherapp.other.LocationUtility
import com.hoseinsadonasl.weatherapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

private const val TAG = "MainFragment"

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.layout_fragment_main), EasyPermissions.PermissionCallbacks {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()

        viewModel.currentWeather.observe(viewLifecycleOwner, {
            //Log.d(TAG, "onViewCreated: ${it.temp}")
        })
    }

    private fun requestPermission() {
        if (LocationUtility.hasPermission(requireContext())) {
            return
        }
            EasyPermissions.requestPermissions(
                this,
                "Accept location permission to use the app",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermission()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


}