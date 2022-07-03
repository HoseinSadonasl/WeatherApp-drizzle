package com.hoseinsadonasl.weatherapp.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoseinsadonasl.weatherapp.R
import com.hoseinsadonasl.weatherapp.databinding.LayoutFragmentMainBinding
import com.hoseinsadonasl.weatherapp.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.hoseinsadonasl.weatherapp.other.LocationUtility
import com.hoseinsadonasl.weatherapp.ui.adapters.MainAdapter
import com.hoseinsadonasl.weatherapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

private const val TAG = "MainFragment"

@AndroidEntryPoint
class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: LayoutFragmentMainBinding
    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_fragment_main,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        setupRecyvlerView()
        observeData()
    }

    private fun setupRecyvlerView() {
        adapter = MainAdapter().apply {
            
        }
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            .apply { binding.daysRv.layoutManager = this }
        DividerItemDecoration(requireContext(), layoutManager.orientation)
            .apply { binding.daysRv.addItemDecoration(this) }
        binding.daysRv.adapter = adapter
    }

    private fun observeData() {
        viewModel.currentWeather.observe(viewLifecycleOwner, {

        })
        viewModel.dailytWeather.observe(viewLifecycleOwner, {
            adapter.submitList(it)
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