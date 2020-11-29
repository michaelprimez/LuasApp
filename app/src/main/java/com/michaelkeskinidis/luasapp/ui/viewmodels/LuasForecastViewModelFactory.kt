package com.michaelkeskinidis.luasapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michaelkeskinidis.luasapp.data.network.LuasForecastNetworkDataSource
import com.michaelkeskinidis.luasapp.data.provider.LocationDirectionProvider

class LuasForecastViewModelFactory(
    private val forecastNetworkDataSource: LuasForecastNetworkDataSource,
    private val locationDirectionProvider: LocationDirectionProvider
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LuasForecastViewModel(forecastNetworkDataSource, locationDirectionProvider) as T
    }
}