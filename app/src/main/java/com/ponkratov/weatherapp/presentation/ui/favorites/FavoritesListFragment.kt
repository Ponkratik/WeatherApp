package com.ponkratov.weatherapp.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ponkratov.weatherapp.R
import com.ponkratov.weatherapp.databinding.FragmentFavoritesListBinding
import com.ponkratov.weatherapp.presentation.extension.addVerticalSpace
import com.ponkratov.weatherapp.presentation.ui.findcity.adapter.CitiesListAdapter
import com.ponkratov.weatherapp.presentation.ui.weatherinfo.WeatherInfoViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesListFragment : Fragment() {
    private var _binding: FragmentFavoritesListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<FavoritesListViewModel>()

    private val weatherInfoViewModel by sharedViewModel<WeatherInfoViewModel>()

    private val adapter by lazy {
        CitiesListAdapter(
            context = requireContext(),
            onItemCityClicked = {
                weatherInfoViewModel.cityFlow.tryEmit(it)
                findNavController().navigate(R.id.action_fragment_favorites_list_to_fragment_weather_info)
            },
            checkedInitialState = true,
            onItemFavoriteCheckedChangedListener = { city, checked ->
                viewModel.onCheckboxFavoritesCheckedChanged(city, checked)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFavoritesListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onViewCreated()

        with(binding) {
            favoritesRecyclerView.addVerticalSpace()
            favoritesRecyclerView.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(query: String): Boolean {
                    viewModel.onQueryTextChanged(query)
                    return true
                }
            })

            viewModel
                .lceFlow
                .onEach { adapter.submitList(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}