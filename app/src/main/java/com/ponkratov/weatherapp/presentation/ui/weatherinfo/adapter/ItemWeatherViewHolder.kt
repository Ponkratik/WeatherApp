package com.ponkratov.weatherapp.presentation.ui.weatherinfo.adapter

import android.provider.Settings.Global.getString
import androidx.recyclerview.widget.RecyclerView
import com.ponkratov.weatherapp.R
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
            c.time = requireNotNull(SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(item.date))
            textviewDayOfWeek.text = when (c.get(Calendar.DAY_OF_WEEK)) {
                2 -> binding.root.context.getString(R.string.day_monday)
                3 -> binding.root.context.getString(R.string.day_tuesday)
                4 -> binding.root.context.getString(R.string.day_wednesday)
                5 -> binding.root.context.getString(R.string.day_thursday)
                6 -> binding.root.context.getString(R.string.day_friday)
                7 -> binding.root.context.getString(R.string.day_saturday)
                1 -> binding.root.context.getString(R.string.day_sunday)
                else -> "Incorrect"
            }

            //textviewTempCode.text = item.weatherCode.toString()
            textviewMaxTemp.text = item.maxTemp
            textviewMinTemp.text = item.minTemp
        }
    }
}