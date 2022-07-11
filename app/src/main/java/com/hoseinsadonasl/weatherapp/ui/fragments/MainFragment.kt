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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
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
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainFragment : Fragment(){

    private lateinit var binding: LayoutFragmentMainBinding

    @Inject
    @Named("MainDailyForecastAdapter")
    lateinit var dailyForecastAdapter: MainDailyForecastAdapter

    @Inject
    @Named("MainHourlyForecastAdapter")
    lateinit var hourlyForecastAdapter: MainHourlyForecastAdapter

    @Inject
    @Named("glideProvider")
    lateinit var glide: RequestManager

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
        setupHourlyForecastRecyvlerView()
        setupDailyForecastRecyvlerView()
        observeData()
    }

    private fun setupHourlyForecastRecyvlerView() {
        binding.hourlyRv.let { recyclerView ->
            recyclerView.adapter = hourlyForecastAdapter
        }
    }

    private fun setupDailyForecastRecyvlerView() {
        val div = ContextCompat.getDrawable(requireContext(), R.drawable.divider_rv)
        binding.daysRv.let { recyclerView ->
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
                .apply {
                    binding.daysRv.addItemDecoration(this)
                    this.setDrawable(div!!)

                }
            recyclerView.adapter = dailyForecastAdapter
        }
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
                windSpeedTv.text =
                    "Wind: " + (weather.current.wind_speed.toString() + "Km/h")
                weatherStatusDescriptionTv.text = weather.current.weather[0].description
                weatherStatusMainTv.text = "Status: " + weather.current.weather[0].main
                hectopascalTv.text = "Pressure: " + weather.current.pressure.toString()
                dewPointTv.text = "Dew point: " + weather.current.dew_poDouble.toString()
            }
            dailyForecastAdapter.submitList(weather.daily)
            hourlyForecastAdapter.submitList(weather.hourly)
        }
    }

    private fun setBackImg(status: String, sunset: Double, sunrise: Double) {
        val currentTimeInMillis = viewModel.currentTimeInMillis
        val imageUrl: String = imageUrl(status, currentTimeInMillis, sunset, sunrise)
        glide.load(imageUrl).into(binding.weatherImg)
    }

    fun imageUrl(
        status: String,
        currentTimeInMillis: Long,
        sunset: Double,
        sunrise: Double
    ): String {
        val imgRes: String
        when (status) {
            "Thunderstorm" -> imgRes = getString(R.string.thunderandlightning)
            "Drizzle" -> imgRes = getString(R.string.drizzle)
            "Rain" -> {
                if (currentTimeInMillis >= sunset.toLong() * 1000 && currentTimeInMillis <= sunrise.toLong() * 1000) {
                    imgRes = getString(R.string.rainynight)
                } else {
                    imgRes = getString(R.string.rainy)
                }
            }
            "Snow" -> imgRes = getString(R.string.snow)
            "Clear" -> {
                if (currentTimeInMillis >= sunset.toLong() * 1000 && currentTimeInMillis <= sunrise.toLong() * 1000) {
                    imgRes = getString(R.string.sunny)
                } else {
                    imgRes = getString(R.string.night)
                }
            }
            "Clouds" -> imgRes = getString(R.string.clouds)
            "Haze", "Fog" -> imgRes = getString(R.string.fog)
            else -> imgRes = getString(R.string.sunny)
        }
        return imgRes
    }




}