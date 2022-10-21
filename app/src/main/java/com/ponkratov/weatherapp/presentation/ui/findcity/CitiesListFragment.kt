package com.ponkratov.weatherapp.presentation.ui.findcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.ponkratov.weatherapp.R
import com.ponkratov.weatherapp.databinding.FragmentCitiesListBinding
import com.ponkratov.weatherapp.domain.model.Lce
import com.ponkratov.weatherapp.presentation.extension.addVerticalSpace
import com.ponkratov.weatherapp.presentation.ui.findcity.adapter.CitiesListAdapter
import com.ponkratov.weatherapp.presentation.ui.weatherinfo.WeatherInfoViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CitiesListFragment : Fragment() {
    private var _binding: FragmentCitiesListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<CitiesListViewModel>()

    private val weatherInfoViewModel by sharedViewModel<WeatherInfoViewModel>()

    //решить косяк с отображением того, что есть в бд без флажка

    private val adapter by lazy {
        CitiesListAdapter(
            context = requireContext(),
            onItemCityClicked = {
                weatherInfoViewModel.cityFlow.tryEmit(it)
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

        viewModel.onViewCreated()

        with(binding) {
            citiesRecyclerView.addVerticalSpace()
            citiesRecyclerView.adapter = adapter
            textSearch.setText("")

            textSearch.addTextChangedListener {
                viewModel.onQueryTextChanged(textSearch.text.toString())
            }

            viewModel
                .lceFlow
                .onEach {
                    when (it) {
                        Lce.Loading -> {
                            circularProgress.visibility = CircularProgressIndicator.VISIBLE
                        }
                        is Lce.Content -> {
                            circularProgress.visibility = CircularProgressIndicator.GONE
                            adapter.submitList(it.data)
                        }
                        is Lce.Error -> {
                            Toast.makeText(requireContext(), "Error while loading data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            /*viewModel
                .dataFlow
                .onEach { adapter.submitList(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}