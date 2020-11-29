package com.michaelkeskinidis.luasapp.data.network

import com.michaelkeskinidis.luasapp.data.network.response.StopInfo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface LuasApi {

    @GET("xml/get.ashx")
    fun getForecastAsync(
        @Query("action") action: String = "forecast",
        @Query("encrypt") encrypt: Boolean = false,
        @Query("stop") stop: String
    ): Deferred<StopInfo>
}