package com.ponkratov.weatherapp.presentation.ui.weatherinfo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ponkratov.weatherapp.databinding.ItemWeatherBinding
import com.ponkratov.weatherapp.domain.model.Weather

class WeatherListAdapter(
    context: Context
) : ListAdapter<Weather, ItemWeatherViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemWeatherViewHolder {
        return ItemWeatherViewHolder(ItemWeatherBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Weather>() {
            override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
                return oldItem.date == newItem.date
                        && oldItem.maxTemp == newItem.maxTemp
                        && oldItem.minTemp == newItem.minTemp
                        && oldItem.weatherCode == newItem.weatherCode
            }

        }
    }
}