package com.ponkratov.weatherapp.presentation.ui.findcity.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ponkratov.weatherapp.databinding.ItemCityBinding
import com.ponkratov.weatherapp.domain.model.City

class ItemCityViewHolder(
    private val binding: ItemCityBinding,
    private val onItemCityClicked: (City) -> Unit,
    private val checkedInitialState: Boolean,
    private val onItemFavoriteCheckedChangedListener: (City, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: City) {
        with(binding) {
            textCity.text = item.name
            textCountry.text = item.country
            checkboxFavorites.isChecked = checkedInitialState

            root.setOnClickListener { onItemCityClicked(item) }
            checkboxFavorites.setOnCheckedChangeListener { _, isChecked -> onItemFavoriteCheckedChangedListener(item, isChecked) }
        }
    }
}