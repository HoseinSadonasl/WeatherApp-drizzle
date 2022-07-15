package com.hoseinsadonasl.weatherapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoseinsadonasl.weatherapp.models.onecall.Weather
import com.hoseinsadonasl.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    @Named("currentTimestamp") private var currentTimeStamp: Long
) : ViewModel() {

    private var _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    val currentTimestamp = currentTimeStamp
    var lat: String? = null
    var lon: String? = null

    init {
        viewModelScope.launch {
            delay(5000)
            getCurrentWeather()
        }
    }

    fun getCurrentWeather() {
        viewModelScope.launch {
            repository.getWeather(this@MainViewModel.lat, this@MainViewModel.lon).flowOn(IO)
                .collect {
                    _weather.postValue(it.body())
                    this@MainViewModel.lat = it.body()?.lat.toString()
                    this@MainViewModel.lon = it.body()?.lon.toString()
                }
        }
    }

    fun getCurrentWeatherByCityName(cityName: String) {
        viewModelScope.launch {
            repository.getCurrentWeatherByCityName(cityName).flowOn(IO).collect {
                lat = it.body()?.coord?.lat.toString()
                lon = it.body()?.coord?.lon.toString()
                getCurrentWeather()
            }
        }
    }
}