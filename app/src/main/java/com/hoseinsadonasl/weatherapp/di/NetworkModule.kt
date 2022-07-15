package com.hoseinsadonasl.weatherapp.di

import com.hoseinsadonasl.weatherapp.Constants.API_KEY
import com.hoseinsadonasl.weatherapp.Constants.BASE_URL
import com.hoseinsadonasl.weatherapp.network.ApiHelper
import com.hoseinsadonasl.weatherapp.network.ApiImpl
import com.hoseinsadonasl.weatherapp.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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