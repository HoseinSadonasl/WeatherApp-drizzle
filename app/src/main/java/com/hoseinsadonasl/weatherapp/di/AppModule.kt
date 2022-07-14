package com.hoseinsadonasl.weatherapp.di

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.bumptech.glide.Glide
import com.hoseinsadonasl.weatherapp.other.LocationUtility
import com.hoseinsadonasl.weatherapp.ui.adapters.MainDailyForecastAdapter
import com.hoseinsadonasl.weatherapp.ui.adapters.MainHourlyForecastAdapter
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
    @Named("locationManager")
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    @Singleton
    @Provides
    @Named("location")
    fun provideLocation(
        @Named("locationManager") locationManager: LocationManager,
        @ApplicationContext context: Context
    ): ArrayList<Double> {
        val latLon = ArrayList<Double>()
        if (LocationUtility.hasPermission(context)) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                5f
            ) { location ->
                latLon.add(location.latitude)
                latLon.add(location.longitude)
            }
        }
        return latLon
    }

    @Singleton
    @Provides
    @Named("glideProvider")
    fun provideGlide(@ApplicationContext context: Context) = Glide.with(context)


    @Singleton
    @Provides
    @Named("MainDailyForecastAdapter")
    fun provideDailyForecastAdapter() = MainDailyForecastAdapter()

    @Singleton
    @Provides
    @Named("MainHourlyForecastAdapter")
    fun provideHourlyForecastAdapter() = MainHourlyForecastAdapter()

    @Singleton
    @Provides
    @Named("exclude")
    fun provideExclude() = "dailyhourly"

    @Singleton
    @Provides
    @Named("currentTimestamp")
    fun provideCurrentTimestamp(): Long = System.currentTimeMillis() / 1000

}