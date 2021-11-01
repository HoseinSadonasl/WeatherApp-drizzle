package com.hoseinsadonasl.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.hoseinsadonasl.weatherapp.repository.DetailRepository

class DetailViewModel(
    val detailRepository: DetailRepository
): ViewModel() {
}