package com.hoseinsadonasl.weatherapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoseinsadonasl.weatherapp.models.Current
import com.hoseinsadonasl.weatherapp.models.Daily
import com.hoseinsadonasl.weatherapp.models.Weather
import com.hoseinsadonasl.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private var _currentWeather = MutableLiveData<Current>()
    val currentWeather: LiveData<Current>
        get() = _currentWeather

    private var _dailytWeather = MutableLiveData<List<Daily>>()
    val dailytWeather: LiveData<List<Daily>>
        get() = _dailytWeather

    private var _timeZone = MutableLiveData<Double>()
    val timeZone: LiveData<Double>
        get() = _timeZone

    private var _temp = MutableLiveData<Double>()
    val temp: LiveData<Double>
        get() = _temp

    private var _feels_like = MutableLiveData<Double>()
    val feels_like: LiveData<Double>
        get() = _feels_like

    private var _sunrise = MutableLiveData<Double>()
    val sunrise: LiveData<Double>
        get() = _sunrise

    private var _sunset = MutableLiveData<Double>()
    val sunset: LiveData<Double>
        get() = _sunset

    private var _humidity = MutableLiveData<Double>()
    val humidity: LiveData<Double>
        get() = _humidity

    private var _dewPoint = MutableLiveData<Double>()
    val dewPoint: LiveData<Double>
        get() = _dewPoint

    private var _uvi = MutableLiveData<Double>()
    val uvi: LiveData<Double>
        get() = _uvi

    private var _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private var _icon = MutableLiveData<String>()
    val icon: LiveData<String>
        get() = _icon

    init {
        getCurrentWeather()
        getDailyWeather()
    }

    private fun getCurrentWeather() {
        CoroutineScope(IO).launch {
            delay(5000)
            repository.getWeather().collect {
                _currentWeather.postValue(it.body()?.current)
                Log.d(TAG, "getDailyWeather: ${it.body()?.current}")
            }
        }
    }

    private fun getDailyWeather() {
        CoroutineScope(IO).launch {
            delay(5000)
            repository.getWeather().collect {
                _dailytWeather.postValue(it.body()?.daily)
                Log.d(TAG, "getDailyWeather: ${it.body()?.daily?.size}")
            }
        }
    }
}