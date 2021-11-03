package com.hoseinsadonasl.weatherapp.di

import android.content.Context
import com.hoseinsadonasl.weatherapp.other.GetCurrentLocation
import com.hoseinsadonasl.weatherapp.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    @Named("latitude")
    fun provideLat(
        @ApplicationContext context: Context
    ): Double = GetCurrentLocation(context).getLatLon()[0]

    @Singleton
    @Provides
    @Named("longitude")
    fun providelon(
        @ApplicationContext context: Context
    ): Double = GetCurrentLocation(context).getLatLon()[1]

    @Singleton
    @Provides
    @Named("exclude")
    fun provideExclude() = "dailyhourlyminutly"

}