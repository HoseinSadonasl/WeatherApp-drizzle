package com.hoseinsadonasl.weatherapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoseinsadonasl.weatherapp.models.Current
import com.hoseinsadonasl.weatherapp.models.Weather
import com.hoseinsadonasl.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        viewModelScope.launch {
            _currentWeather.value = repository.getWeather().body()?.current
            Log.d(TAG, "getCurrentWeather: ${repository.getWeather().body()}")

        }
    }
}