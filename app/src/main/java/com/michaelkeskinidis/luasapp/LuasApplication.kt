package com.michaelkeskinidis.luasapp

import android.app.Application
import com.michaelkeskinidis.luasapp.data.network.*
import com.michaelkeskinidis.luasapp.data.network.interceptor.ConnectivityInterceptor
import com.michaelkeskinidis.luasapp.data.network.interceptor.ConnectivityInterceptorImpl
import com.michaelkeskinidis.luasapp.data.provider.LocationDirectionProvider
import com.michaelkeskinidis.luasapp.data.provider.LocationDirectionProviderImpl
import com.michaelkeskinidis.luasapp.ui.viewmodels.LuasForecastViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class LuasApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@LuasApplication))

        bind<LocationDirectionProvider>() with singleton { LocationDirectionProviderImpl() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { LuasApiService(instance()) }
        bind<LuasForecastNetworkDataSource>() with singleton { LuasForecastNetworkDataSourceImpl(instance()) }
        bind() from provider { LuasForecastViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()

    }
}