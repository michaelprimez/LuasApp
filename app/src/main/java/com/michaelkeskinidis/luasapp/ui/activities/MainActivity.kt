package com.michaelkeskinidis.luasapp.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.michaelkeskinidis.luasapp.R
import com.michaelkeskinidis.luasapp.databinding.ActivityMainBinding
import com.michaelkeskinidis.luasapp.databinding.FragmentLuasForecastBinding
import com.michaelkeskinidis.luasapp.internal.NoConnectivityException
import com.michaelkeskinidis.luasapp.ui.base.ScopedActivity
import com.michaelkeskinidis.luasapp.ui.dialogs.DialogCallback
import com.michaelkeskinidis.luasapp.ui.dialogs.NoInternetConnectionDialog
import com.michaelkeskinidis.luasapp.ui.viewmodels.LuasForecastViewModel
import com.michaelkeskinidis.luasapp.ui.viewmodels.LuasForecastViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_luas_forecast.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class MainActivity: ScopedActivity(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var navController: NavController

    private val viewModelFactory: LuasForecastViewModelFactory by instance()
    private lateinit var viewModel: LuasForecastViewModel

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        viewModel = ViewModelProvider(this@MainActivity, viewModelFactory).get(LuasForecastViewModel::class.java)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        bindUI()
        fab.setOnClickListener { view ->
            refresh()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    private fun bindUI() = launch {
        val recyclerView: RecyclerView = tramList as RecyclerView
        val luasForecast = viewModel.luasForecast.await()

        luasForecast.observe(this@MainActivity, Observer { stopInfo ->
            if (stopInfo == null) return@Observer
            binding.content.stopInfo = stopInfo
        })
    }

    private fun refresh() = launch {
        try {
            viewModel.fetchCurrentForecast()
        } catch (e: NoConnectivityException) {
            NoInternetConnectionDialog.newInstance(object : DialogCallback {
                override fun onOK() {

                }
            }).show(
                this@MainActivity.supportFragmentManager.beginTransaction(),
                "NoInternetConnectionDialog"
            )
        }
    }
}