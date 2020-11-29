package com.michaelkeskinidis.luasapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.michaelkeskinidis.luasapp.data.network.LuasForecastNetworkDataSource
import com.michaelkeskinidis.luasapp.data.provider.LocationDirectionProvider
import com.resocoder.forecastmvvm.internal.lazyDeferred
import kotlinx.coroutines.runBlocking

class LuasForecastViewModel(
        private val luasForecastNetworkDataSource: LuasForecastNetworkDataSource,
        val locationDirectionProvider: LocationDirectionProvider
): ViewModel() {
    val luasForecast by lazyDeferred {
        luasForecastNetworkDataSource.downloadCurrentForecast
    }

    suspend fun fetchCurrentForecast() {
        luasForecastNetworkDataSource.fetchCurrentForecast(locationDirectionProvider.getLocation().location)
    }
}