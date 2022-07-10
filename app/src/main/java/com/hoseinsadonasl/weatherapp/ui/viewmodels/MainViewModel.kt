package com.hoseinsadonasl.weatherapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoseinsadonasl.weatherapp.models.Daily
import com.hoseinsadonasl.weatherapp.models.Weather
import com.hoseinsadonasl.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    @Named("currentTimeInMillis") private var currentTimeI: Long
) : ViewModel() {

    private var _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private var _dailyWeather = MutableLiveData<List<Daily>>()
    val dailyWeather: LiveData<List<Daily>>
        get() = _dailyWeather

    val currentTimeInMillis = currentTimeI

    init {
        getCurrentWeather()
        getDailyWeather()
    }

    private fun getCurrentWeather() {
        CoroutineScope(IO).launch {
            delay(5000)
            repository.getWeather().collect {
                _weather.postValue(it.body())
            }
        }
    }


    private fun getDailyWeather() {
        CoroutineScope(IO).launch {
            delay(5000)
            repository.getWeather().collect {
                _dailyWeather.postValue(it.body()?.daily)
            }
        }
    }
}