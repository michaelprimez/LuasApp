package com.michaelkeskinidis.luasapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.michaelkeskinidis.luasapp.R
import com.michaelkeskinidis.luasapp.data.network.response.Tram
import com.michaelkeskinidis.luasapp.databinding.ItemTramBinding
import com.michaelkeskinidis.luasapp.ui.adapters.RecycleViewStates.STATE_EMPTY
import com.michaelkeskinidis.luasapp.ui.adapters.RecycleViewStates.STATE_LOADING
import com.michaelkeskinidis.luasapp.ui.adapters.RecycleViewStates.VIEW_TYPE_ITEM


class TramAdapter(private var tramList: List<Tram>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @RecycleViewStates.State
    private var state = STATE_LOADING

    fun setTramList(tramList: List<Tram>) {
        this.tramList = tramList
        state = STATE_EMPTY
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            STATE_LOADING -> {
                ViewHolderEmpty(layoutInflater.inflate(R.layout.view_loading, parent, false) as ConstraintLayout)
            }
            VIEW_TYPE_ITEM -> {
                ItemTramViewHolder(ItemTramBinding.inflate(layoutInflater, parent, false))
            } else -> ViewHolderEmpty(layoutInflater.inflate(R.layout.view_empty, parent, false) as ConstraintLayout)
        }

    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                (holder as ItemTramViewHolder).bind(tramList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return if (tramList.isEmpty()) 1 else tramList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (tramList.isEmpty()) state else VIEW_TYPE_ITEM
    }

    class ItemTramViewHolder(private val binding: ItemTramBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tram: Tram) {
            binding.tram = tram
            binding.executePendingBindings()
        }
    }

    class ViewHolderEmpty(itemLoading: ConstraintLayout) : RecyclerView.ViewHolder(itemLoading)
}