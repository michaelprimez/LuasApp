package com.michaelkeskinidis.luasapp

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.michaelkeskinidis.luasapp.data.network.LuasApi
import com.michaelkeskinidis.luasapp.mockdata.MockConnectivityInterceptorOnline
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
class LuasNetworkClientTest {

    private var mockWebServer = MockWebServer()

    private lateinit var apiService: LuasApi

    @Before
    fun setup() {
        mockWebServer.start()
        apiService = createMockClient(mockWebServer, MockConnectivityInterceptorOnline())
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldParseResponseDataCorrectly() = runBlocking {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(strStopInfo)

        mockWebServer.enqueue(response)

        val stopInfo = apiService.getForecastAsync(stop = "sti").await()

        assert(stopInfo.stop == "Stillorgan")
        assert(stopInfo.message == "Green Line services operating normally")
        assert(stopInfo.created == "2020-11-28T08:32:23")
        assert(stopInfo.direction.size == 2)

        assert(stopInfo.direction[0].name == "Inbound")
        assert(stopInfo.direction[0].trams.size == 1)
        assert(stopInfo.direction[0].trams[0].dueMins == "9")
        assert(stopInfo.direction[0].trams[0].destination == "Broombridge")

        assert(stopInfo.direction[1].name == "Outbound")
        assert(stopInfo.direction[1].trams.size == 4)
        assert(stopInfo.direction[1].trams[0].dueMins == "DUE")
        assert(stopInfo.direction[1].trams[0].destination == "Sandyford")
        assert(stopInfo.direction[1].trams[1].dueMins == "4")
        assert(stopInfo.direction[1].trams[1].destination == "Bride's Glen")
        assert(stopInfo.direction[1].trams[2].dueMins == "11")
        assert(stopInfo.direction[1].trams[2].destination == "Sandyford")
        assert(stopInfo.direction[1].trams[3].dueMins == "18")
        assert(stopInfo.direction[1].trams[3].destination == "Bride's Glen")
    }
}