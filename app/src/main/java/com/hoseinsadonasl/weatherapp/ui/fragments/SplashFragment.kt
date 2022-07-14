package com.hoseinsadonasl.weatherapp.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hoseinsadonasl.weatherapp.R
import com.hoseinsadonasl.weatherapp.databinding.LayoutSplashFragmentBinding
import com.hoseinsadonasl.weatherapp.other.Constants
import com.hoseinsadonasl.weatherapp.other.LocationUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class SplashFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: LayoutSplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_splash_fragment,
            container,
            false
        )

        requestPermission()

        return binding.root
    }

    private fun requestPermission() {
        if (LocationUtility.hasPermission(requireContext())) {
            navigateToMainFragment()
            return
        }
        EasyPermissions.requestPermissions(
            this,
            "Accept location permission to use drizzle. If you don't do it, it will be closed",
            Constants.REQUEST_CODE_LOCATION_PERMISSION,
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

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        requireActivity().finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        navigateToMainFragment()
    }

    private fun navigateToMainFragment() = CoroutineScope(Dispatchers.Main).launch {
        delay(3000)
        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
    }

}