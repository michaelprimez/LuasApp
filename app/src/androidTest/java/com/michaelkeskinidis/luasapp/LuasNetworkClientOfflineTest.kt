package com.michaelkeskinidis.luasapp

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.michaelkeskinidis.luasapp.data.network.LuasApi
import com.michaelkeskinidis.luasapp.internal.NoConnectivityException
import com.michaelkeskinidis.luasapp.mockdata.MockConnectivityInterceptorOffline
import com.michaelkeskinidis.luasapp.mockdata.createMockClient
import com.michaelkeskinidis.luasapp.mockdata.strStopInfo
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4ClassRunner::class)
class LuasNetworkClientOfflineTest {

    private var mockWebServer = MockWebServer()

    private lateinit var apiService: LuasApi

    @Before
    fun setup() {
        mockWebServer.start()
        apiService = createMockClient(mockWebServer, MockConnectivityInterceptorOffline())
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldThrowNoConnectivityException() = runBlocking {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(strStopInfo)

        mockWebServer.enqueue(response)

        val results = try {
            apiService.getForecastAsync(stop = "sti").await()
        } catch (ex: Throwable) {
            ex
        }

        assert(results is NoConnectivityException)
        assert((results as NoConnectivityException).message == "Internet Not Available")
    }
}