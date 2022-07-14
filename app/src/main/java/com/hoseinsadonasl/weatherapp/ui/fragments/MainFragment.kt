package com.hoseinsadonasl.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.hoseinsadonasl.weatherapp.R
import com.hoseinsadonasl.weatherapp.databinding.LayoutFragmentMainBinding
import com.hoseinsadonasl.weatherapp.ui.adapters.MainDailyForecastAdapter
import com.hoseinsadonasl.weatherapp.ui.adapters.MainHourlyForecastAdapter
import com.hoseinsadonasl.weatherapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainFragment : Fragment(){

    @Inject
    @Named("MainDailyForecastAdapter")
    lateinit var dailyForecastAdapter: MainDailyForecastAdapter

    @Inject
    @Named("MainHourlyForecastAdapter")
    lateinit var hourlyForecastAdapter: MainHourlyForecastAdapter

    @Inject
    @Named("glideProvider")
    lateinit var glide: RequestManager

    private lateinit var binding: LayoutFragmentMainBinding
    private var finishActivity = false
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.cityNameEt.let {
                    if (it.isFocused) {
                        it.text?.clear()
                    }
                }
                Toast.makeText(requireContext(), "Press back again to close drizzle", Toast.LENGTH_SHORT).show()
                CoroutineScope(Main).launch {
                    finishActivity = true
                    delay(1500)
                    finishActivity = false
                }
                if (finishActivity) {
                    requireActivity().finishAffinity()
                }
            }
        })
    }

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
        binding.refreshBtn.setOnClickListener {
            it.animate().rotation(360f).start()
            showProgressBars()
            viewModel.getCurrentWeather()
        }

        binding.cityNameTextInputLayout.let {
            it.setEndIconOnClickListener {
                viewModel.getCurrentWeatherByCityName(binding.cityNameEt.text.toString())
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHourlyForecastRecyvlerView()
        setupDailyForecastRecyvlerView()
        observeData()
    }

    private fun showProgressBars() {
        binding.tempPb.visibility = VISIBLE
        binding.dailyRvPb.visibility = VISIBLE
        binding.hourlyRvPb.visibility = VISIBLE
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
            binding.apply {
                tempPb.visibility = GONE
                dailyRvPb.visibility = GONE
                hourlyRvPb.visibility = GONE
                refreshBtn.visibility = VISIBLE

                setBackImg(
                    weather.current.weather[0].main,
                    weather.current.sunset,
                    weather.current.sunrise
                )

                tempTv.text = ((weather.current.temp.toInt() - 273).toString() + "°")
                tempMaxMinTv.text = "Max/Min: " +
                        ((weather.daily.get(0).temp.max.toInt() - 273).toString() + "°/" +
                                (weather.daily.get(0).temp.min.toInt() - 273).toString() + "°")
                locationNameTv.text = weather.timezone.substringAfter("/")
                uvTv.text = "UV Index: " + weather.current.uvi.toString()
                humidityTv.text = "Humidity: " + weather.current.humidity.toInt().toString() + "%"
                visibilityTv.text = "Visibility: " + (weather.current.visibility / 1000).toString() + "Km"
                windSpeedTv.text = "Wind: " + (weather.current.wind_speed.toString() + "Km/h")
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
        val currentTimeInMillis = viewModel.currentTimestamp
        val imageUrl: String =
            imageUrl(status, currentTimeInMillis, sunset.toLong(), sunrise.toLong())
        glide.load(imageUrl).into(binding.weatherImg)
    }

    fun imageUrl(
        status: String,
        currentTimestamp: Long,
        sunset: Long,
        sunrise: Long
    ): String {
        val imgRes: String
        when (status) {
            "Thunderstorm" -> imgRes = getString(R.string.thunderandlightning)
            "Drizzle" -> imgRes = getString(R.string.drizzle)
            "Rain" -> {
                if (currentTimestamp in sunset..sunrise) {
                    imgRes = getString(R.string.rainy)
                } else {
                    imgRes = getString(R.string.rainynight)
                }
            }
            "Snow" -> imgRes = getString(R.string.snow)
            "Clear" -> {
                if (currentTimestamp in sunset..sunrise) {
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