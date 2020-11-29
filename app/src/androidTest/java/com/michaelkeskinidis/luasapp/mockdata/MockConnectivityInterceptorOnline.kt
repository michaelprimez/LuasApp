package com.michaelkeskinidis.luasapp.mockdata

import com.michaelkeskinidis.luasapp.data.network.interceptor.ConnectivityInterceptor
import com.michaelkeskinidis.luasapp.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class MockConnectivityInterceptorOnline: ConnectivityInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}