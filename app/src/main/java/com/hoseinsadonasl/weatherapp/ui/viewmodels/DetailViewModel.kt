package com.hoseinsadonasl.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.hoseinsadonasl.weatherapp.repository.DetailRepository
import javax.inject.Named

class DetailViewModel(
    val detailRepository: DetailRepository
    ,@Named("latitude") val lat:String

): ViewModel() {



}