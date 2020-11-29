package com.michaelkeskinidis.luasapp.mockdata

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.michaelkeskinidis.luasapp.data.network.CONNECT_TIMEOUT
import com.michaelkeskinidis.luasapp.data.network.LuasApi
import com.michaelkeskinidis.luasapp.data.network.READ_TIMEOUT
import com.michaelkeskinidis.luasapp.data.network.WRITE_TIMEOUT
import com.michaelkeskinidis.luasapp.data.network.interceptor.ConnectivityInterceptor
import com.michaelkeskinidis.luasapp.data.network.response.Direction
import com.michaelkeskinidis.luasapp.data.network.response.StopInfo
import com.michaelkeskinidis.luasapp.data.network.response.Tram
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

fun createMockClient(mockWebServer: MockWebServer, connectivityInterceptor: ConnectivityInterceptor): LuasApi =
    Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(
            OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(
            TikXmlConverterFactory.create(
                TikXml.Builder()
                    .exceptionOnUnreadXml(false)
                    .addTypeConverter(String::class.java, HtmlEscapeStringConverter())
                    .build()
            )
        )
        .build()
        .create(LuasApi::class.java)

val strStopInfo = """
    <stopInfo created="2020-11-28T08:32:23" stop="Stillorgan" stopAbv="STI">
    	<message>Green Line services operating normally</message>
    	<direction name="Inbound">
    		<tram dueMins="9" destination="Broombridge" />
    	</direction>
    	<direction name="Outbound">
    		<tram dueMins="DUE" destination="Sandyford" />
    		<tram dueMins="4" destination="Bride's Glen" />
    		<tram dueMins="11" destination="Sandyford" />
    		<tram dueMins="18" destination="Bride's Glen" />
    	</direction>
    </stopInfo>
""".trimIndent()

val tram1 = Tram("DUE", "Sandyford")
val tram2 = Tram("4", "Bride's Glen")
val tram3 = Tram("11", "Sandyford")
val tram4 = Tram("18", "Bride's Glen")

val tramListOutbound = listOfNotNull(tram1, tram2, tram3, tram4)
val directionOutbound = Direction("Outbound", tramListOutbound)

val tram5 = Tram("9", "Broombridge")
val tramListInbound = listOfNotNull(tram5)
val directionInbound = Direction("Inbound", tramListInbound)

val stopInfo = StopInfo(
    "2020-11-28T08:32:23",
    "Stillorgan",
    "Green Line services operating normally",
    listOf(directionInbound, directionOutbound))
