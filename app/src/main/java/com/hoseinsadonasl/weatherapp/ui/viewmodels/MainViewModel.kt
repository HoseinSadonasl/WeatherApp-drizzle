package com.hoseinsadonasl.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.hoseinsadonasl.weatherapp.repository.MainRepository

class MainViewModel(
    val mainRepository: MainRepository
): ViewModel() {
}