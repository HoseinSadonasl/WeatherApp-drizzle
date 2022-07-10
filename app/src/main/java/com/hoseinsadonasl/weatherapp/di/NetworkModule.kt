package com.hoseinsadonasl.weatherapp.di

import android.Manifest
import android.app.Activity
import android.content.Context
import com.hoseinsadonasl.weatherapp.network.ApiHelper
import com.hoseinsadonasl.weatherapp.network.ApiImpl
import com.hoseinsadonasl.weatherapp.network.WeatherApi
import com.hoseinsadonasl.weatherapp.other.Constants
import com.hoseinsadonasl.weatherapp.other.Constants.API_KEY
import com.hoseinsadonasl.weatherapp.other.Constants.BASE_URL
import com.hoseinsadonasl.weatherapp.other.LocationUtility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("base_url")
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    @Named("appid")
    fun provideAppId() = API_KEY

    @Singleton
    @Provides
    fun provideRetrofit(@Named("base_url") baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(apiImpl: ApiImpl): ApiHelper = apiImpl


}