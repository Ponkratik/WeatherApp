package com.ponkratov.weatherapp.presentation.ui.findcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ponkratov.weatherapp.R
import com.ponkratov.weatherapp.databinding.FragmentCitiesListBinding
import com.ponkratov.weatherapp.presentation.extension.addVerticalSpace
import com.ponkratov.weatherapp.presentation.ui.findcity.adapter.CitiesListAdapter
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class CitiesListFragment : Fragment() {
    private var _binding: FragmentCitiesListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<CitiesListViewModel>()

    //решить косяк с отображением того, что есть в бд без флажка

    private val adapter by lazy {
        CitiesListAdapter(
            context = requireContext(),
            onItemCityClicked = {
                findNavController().navigate(R.id.action_fragment_cities_list_to_fragment_weather_info)
            },
            checkedInitialState = false,
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
        return FragmentCitiesListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            citiesRecyclerView.addVerticalSpace()
            citiesRecyclerView.adapter = adapter

            textSearch.addTextChangedListener {
                viewModel.onQueryTextChanged(textSearch.text.toString())
            }

            viewModel
                .dataFlow
                .onEach { adapter.submitList(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}