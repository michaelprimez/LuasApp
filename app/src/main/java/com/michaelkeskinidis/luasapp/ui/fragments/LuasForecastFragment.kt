package com.michaelkeskinidis.luasapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelkeskinidis.luasapp.databinding.FragmentLuasForecastBinding
import com.michaelkeskinidis.luasapp.internal.NoConnectivityException
import com.michaelkeskinidis.luasapp.ui.adapters.TramAdapter
import com.michaelkeskinidis.luasapp.ui.base.ScopedFragment
import com.michaelkeskinidis.luasapp.ui.dialogs.DialogCallback
import com.michaelkeskinidis.luasapp.ui.dialogs.NoInternetConnectionDialog
import com.michaelkeskinidis.luasapp.ui.viewmodels.LuasForecastViewModel
import com.michaelkeskinidis.luasapp.ui.viewmodels.LuasForecastViewModelFactory
import kotlinx.android.synthetic.main.fragment_luas_forecast.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class LuasForecastFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: LuasForecastViewModelFactory by instance()
    private lateinit var viewModel: LuasForecastViewModel

    private var _binding: FragmentLuasForecastBinding? = null
    private val binding get() = _binding!!

    private final val tramAdapter = TramAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLuasForecastBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this@LuasForecastFragment, viewModelFactory).get(LuasForecastViewModel::class.java)
        bindUI()
        refresh()
    }

    private fun bindUI() = launch {
        val recyclerView: RecyclerView = tramList as RecyclerView
        val luasForecast = viewModel.luasForecast.await()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@LuasForecastFragment.context)
            adapter = tramAdapter
        }
        luasForecast.observe(viewLifecycleOwner, Observer { stopInfo ->
            if (stopInfo == null) return@Observer
            binding.stopInfo = stopInfo
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@LuasForecastFragment.context)
                val locationDirectionProvider = viewModel.locationDirectionProvider.getLocation()
                val directions = luasForecast.value?.direction?.filter { it.name.equals(locationDirectionProvider.direction, ignoreCase = true) }
                tramAdapter.setTramList(directions?.get(0)?.trams ?: listOf())
            }
        })
        swipe_container.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() = launch {
        try {
            viewModel.fetchCurrentForecast()
            swipe_container.isRefreshing = false
        } catch (e: NoConnectivityException) {
            NoInternetConnectionDialog.newInstance(object : DialogCallback {
                override fun onOK() {
                    swipe_container.isRefreshing = false
                    tramAdapter.setTramList(listOf())
                }
            }).show(requireActivity().supportFragmentManager.beginTransaction(), "NoInternetConnectionDialog")
        }
    }
}