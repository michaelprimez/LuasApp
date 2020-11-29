package com.michaelkeskinidis.luasapp.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.michaelkeskinidis.luasapp.data.network.interceptor.ConnectivityInterceptor
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object LuasApiService {
    operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
    ): LuasApi {
        return Retrofit.Builder()
            .baseUrl(URL)
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
            .build().create(LuasApi::class.java)
    }
}