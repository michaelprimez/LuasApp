package com.michaelkeskinidis.luasapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michaelkeskinidis.luasapp.data.network.response.StopInfo
import com.michaelkeskinidis.luasapp.internal.NoConnectivityException

class LuasForecastNetworkDataSourceImpl(
        private val luasApi: LuasApi): LuasForecastNetworkDataSource {

    private val _downloadCurrentForecast = MutableLiveData<StopInfo>();

    override val downloadCurrentForecast: LiveData<StopInfo>
        get() = _downloadCurrentForecast

    override suspend fun fetchCurrentForecast(stop: String) {
        try {
            val fetchCurrentForecast = luasApi.getForecastAsync(stop = stop).await();
            _downloadCurrentForecast.postValue(fetchCurrentForecast)
        } catch (e: NoConnectivityException) {
            throw NoConnectivityException(e.message)
        }
    }
}