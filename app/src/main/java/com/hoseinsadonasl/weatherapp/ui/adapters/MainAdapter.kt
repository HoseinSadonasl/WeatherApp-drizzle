package com.hoseinsadonasl.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoseinsadonasl.weatherapp.databinding.LayoutMainRecyclerViewListItemBinding
import com.hoseinsadonasl.weatherapp.models.Daily

class MainAdapter : ListAdapter<Daily, MainAdapter.ViewHolder>(DiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutMainRecyclerViewListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView5.text = getItem(position).temp.toString()
        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(val binding: LayoutMainRecyclerViewListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}

object DiffUtilCallBack : DiffUtil.ItemCallback<Daily>() {
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

}