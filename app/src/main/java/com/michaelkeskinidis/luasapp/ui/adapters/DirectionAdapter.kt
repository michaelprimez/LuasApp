package com.michaelkeskinidis.luasapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelkeskinidis.luasapp.R
import com.michaelkeskinidis.luasapp.data.network.response.Direction
import com.michaelkeskinidis.luasapp.databinding.ItemDirectionBinding
import com.michaelkeskinidis.luasapp.ui.adapters.RecycleViewStates.STATE_LOADING
import com.michaelkeskinidis.luasapp.ui.adapters.RecycleViewStates.VIEW_TYPE_ITEM


class DirectionAdapter(private var directionList: List<Direction>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @RecycleViewStates.State
    private var state = STATE_LOADING

    fun setDirections(directionList: List<Direction>) {
        this.directionList = directionList
        state = RecycleViewStates.STATE_EMPTY
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
                DirectionViewHolder(ItemDirectionBinding.inflate(layoutInflater, parent, false))
            } else -> ViewHolderEmpty(layoutInflater.inflate(R.layout.view_empty, parent, false) as ConstraintLayout)
        }

    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                (holder as DirectionViewHolder).bind(directionList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return if (directionList.isEmpty()) 1 else directionList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (directionList.isEmpty()) state else VIEW_TYPE_ITEM
    }

    class DirectionViewHolder(private val binding: ItemDirectionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(direction: Direction) {
            binding.direction = direction
            binding.tramList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = TramAdapter(direction.trams)
            }

            binding.executePendingBindings()
        }
    }

    class ViewHolderEmpty(itemLoading: ConstraintLayout) : RecyclerView.ViewHolder(itemLoading)
}