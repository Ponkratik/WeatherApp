package com.ponkratov.weatherapp.presentation.ui.weatherinfo.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ponkratov.weatherapp.databinding.ItemCityBinding
import com.ponkratov.weatherapp.databinding.ItemWeatherBinding
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.model.Weather
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ItemWeatherViewHolder(
    private val binding: ItemWeatherBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Weather) {
        with(binding) {
            val c = Calendar.getInstance()
            c.time = requireNotNull(SimpleDateFormat("yyyy-MM-dd").parse(item.date))
            textviewDayOfWeek.text = when (c.get(Calendar.DAY_OF_WEEK)) {
                2 -> "Monday"
                3 -> "Tuesday"
                4 -> "Wednesday"
                5 -> "Thursday"
                6 -> "Friday"
                7 -> "Saturday"
                1 -> "Sunday"
                else -> "Incorrect"
            }

            textviewTempCode.text = item.weatherCode.toString()
            textviewMaxTemp.text = item.maxTemp
            textviewMinTemp.text = item.minTemp
        }
    }
}