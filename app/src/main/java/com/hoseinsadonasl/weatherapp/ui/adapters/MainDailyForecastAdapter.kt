package com.hoseinsadonasl.weatherapp.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoseinsadonasl.weatherapp.databinding.LayoutMainRecyclerViewListItemBinding
import com.hoseinsadonasl.weatherapp.formatDailyAdapterTime
import com.hoseinsadonasl.weatherapp.formatTime
import com.hoseinsadonasl.weatherapp.models.Daily

class MainDailyForecastAdapter : ListAdapter<Daily, MainDailyForecastAdapter.ViewHolder>(DailyAdapterDiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutMainRecyclerViewListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.dateTv.text = formatDailyAdapterTime(getItem(position).dt.toInt().toLong())
        holder.binding.statusTv.text = getItem(position).weather[0].description
        holder.binding.tempTv.text =
            ((getItem(position).temp.max.toInt() - 273).toString()) + "°/" + ((getItem(position).temp.min.toInt() - 273).toString()) + "°"
        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(val binding: LayoutMainRecyclerViewListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}

object DailyAdapterDiffUtilCallBack : DiffUtil.ItemCallback<Daily>() {
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

}