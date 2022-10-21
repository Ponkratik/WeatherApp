package com.ponkratov.weatherapp.presentation.ui.favorites.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ponkratov.weatherapp.databinding.ItemCityBinding
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.presentation.ui.findcity.adapter.ItemCityViewHolder

class FavoritesListAdapter(
    context: Context,
    private val onItemCityClicked: (City) -> Unit,
    private val checkedInitialState: Boolean,
    private val onItemFavoriteCheckedChangedListener: (City, Boolean) -> Unit
)  : ListAdapter<City, ItemCityViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCityViewHolder {
        return ItemCityViewHolder(
            ItemCityBinding.inflate(layoutInflater, parent, false),
            onItemCityClicked,
            checkedInitialState,
            onItemFavoriteCheckedChangedListener
        )
    }

    override fun onBindViewHolder(holder: ItemCityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<City>() {
            override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
                return oldItem.name == newItem.name && oldItem.country == newItem.country
            }
        }
    }
}