package com.hoseinsadonasl.weatherapp.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.hoseinsadonasl.weatherapp.ui.adapters.MainDailyForecastAdapter
import com.hoseinsadonasl.weatherapp.ui.adapters.MainHourlyForecastAdapter
import com.hoseinsadonasl.weatherapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: LayoutFragmentMainBinding
    private lateinit var dailyForecastAdapter: MainDailyForecastAdapter
    private lateinit var hourlyForecastAdapter: MainHourlyForecastAdapter

    private val viewModel: MainViewModel by viewModels()

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
        setupHourlyForecastRecyvlerView()
        setupDailyForecastRecyvlerView()
        observeData()
    }

    private fun setupHourlyForecastRecyvlerView() {
        hourlyForecastAdapter = MainHourlyForecastAdapter()
        binding.hourlyRv.let { recyclerView ->
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
                .apply { binding.hourlyRv.addItemDecoration(this) }
            recyclerView.adapter = hourlyForecastAdapter
        }
    }

    private fun setupDailyForecastRecyvlerView() {
        dailyForecastAdapter = MainDailyForecastAdapter()
        binding.daysRv.adapter = dailyForecastAdapter
    }

    private fun observeData() {
        viewModel.weather.observe(viewLifecycleOwner) { weather ->
            binding.tempPb.visibility = GONE
            binding.dailyRvPb.visibility = GONE
            binding.hourlyRvPb.visibility = GONE
            binding.apply {
                setBackImg(
                    weather.current.weather[0].main,
                    weather.current.sunset,
                    weather.current.sunrise
                )
                tempTv.text = ((weather.current.temp.toInt() - 273).toString() + "°")
                tempMaxMinTv.text = "Max/Min: " +
                    ((weather.daily.get(0).temp.max.toInt() - 273).toString() + "°/" +
                            (weather.daily.get(0).temp.min.toInt() - 273).toString() + "°")
                locationNameTv.text = weather.timezone
                uvTv.text = "UV Index: " + weather.current.uvi.toString()
                humidityTv.text = "Humidity: " + weather.current.humidity.toString() + "%"
                visibilityTv.text =
                    "Visibility: " + (weather.current.visibility / 1000).toString() + "Km"
                //var windSpeed = if ()
                windSpeedTv.text =
                    "Wind: " + (weather.current.wind_speed.toString() + "Km/h")
                weatherStatusDescriptionTv.text = weather.current.weather[0].description
                weatherStatusMainTv.text = "Status: " + weather.current.weather[0].main
                hectopascalTv.text = "Pressure: " + weather.current.pressure.toString()
                dewPointTv.text = "Dew point: " + weather.current.dew_poDouble.toString()
            }
            hourlyForecastAdapter.submitList(weather.hourly)
        }
        viewModel.dailytWeather.observe(viewLifecycleOwner) {
            dailyForecastAdapter.submitList(it)
        }
    }

    private fun setBackImg(status: String, sunset: Double, sunrise: Double) {
        val time = System.currentTimeMillis()
        var imgRes = R.drawable.sunny
        when (status) {
            "Thunderstorm" -> imgRes = R.drawable.thunder_and_lightning
            "Drizzle" -> imgRes = R.drawable.cloudy
            "Rain" -> {
                if (time >= sunset && time <= sunrise) {
                    imgRes = R.drawable.rainy_night
                } else {
                    imgRes = R.drawable.rainy
                }
            }
            "Snow" -> imgRes = R.drawable.rainy
            "Clear" -> {
                if (time >= sunset && time <= sunrise) {
                    imgRes = R.drawable.night
                } else {
                    imgRes = R.drawable.sunny
                }
            }
            "Clouds" -> imgRes = R.drawable.cloudy
            "Haze", "Fog" -> imgRes = R.drawable.cloudy
            else -> imgRes = R.drawable.sunny
        }
        binding.weatherImg.setImageDrawable(ContextCompat.getDrawable(requireContext(), imgRes))
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