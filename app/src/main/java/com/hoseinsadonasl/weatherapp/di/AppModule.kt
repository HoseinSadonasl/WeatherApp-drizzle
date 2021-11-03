package com.hoseinsadonasl.weatherapp.di

import android.content.Context
import com.hoseinsadonasl.weatherapp.other.GetCurrentLocation
import com.hoseinsadonasl.weatherapp.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @Named("latitude")
    fun provideLat(
        @ApplicationContext context: Context
    ): String = "33.44" //GetCurrentLocation(context).getLatLon()[0].toString()

    @Singleton//هin kuftya chye  baba dashtam test mikrdm fuck akhmmmmmm
    @Provides
    @Named("longitude")
    fun providelon(
        @ApplicationContext context: Context
    ): String = "94.04" //GetCurrentLocation(context).getLatLon()[1].toString()

    @Singleton
    @Provides
    @Named("exclude")
    fun provideExclude() = "dailyhourlyminutly"

}