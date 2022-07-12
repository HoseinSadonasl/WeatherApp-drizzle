package com.hoseinsadonasl.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoseinsadonasl.weatherapp.databinding.LayoutHourlyForcastRvItemBinding
import com.hoseinsadonasl.weatherapp.formatTime
import com.hoseinsadonasl.weatherapp.models.onecall.Hourly

class MainHourlyForecastAdapter :
    ListAdapter<Hourly, MainHourlyForecastAdapter.ViewHolder>(HourlyAdapterDiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutHourlyForcastRvItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tempTv.text = (getItem(position).temp.toInt() - 273).toString() + "°"
        holder.binding.statusTv.text = getItem(position).weather[0].description
        val time = formatTime(getItem(position).dt.toLong())
        holder.binding.timeTv.text = time
        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(val binding: LayoutHourlyForcastRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

object HourlyAdapterDiffUtilCallBack : DiffUtil.ItemCallback<Hourly>() {
    override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return oldItem == newItem
    }

}