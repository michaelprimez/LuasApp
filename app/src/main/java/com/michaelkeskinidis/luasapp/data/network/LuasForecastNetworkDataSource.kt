package com.michaelkeskinidis.luasapp.data.network

import androidx.lifecycle.LiveData
import com.michaelkeskinidis.luasapp.data.network.response.StopInfo
import java.time.ZonedDateTime

interface LuasForecastNetworkDataSource {
    val downloadCurrentForecast: LiveData<StopInfo>

    suspend fun fetchCurrentForecast(
            stop: String
    )
}