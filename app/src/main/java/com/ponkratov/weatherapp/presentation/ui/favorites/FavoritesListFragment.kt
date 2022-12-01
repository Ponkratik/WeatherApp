package com.ponkratov.weatherapp.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ponkratov.weatherapp.databinding.FragmentFavoritesListBinding
import com.ponkratov.weatherapp.presentation.extension.addVerticalSpace
import com.ponkratov.weatherapp.presentation.ui.findcity.adapter.CitiesListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesListFragment : Fragment() {
    private var _binding: FragmentFavoritesListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<FavoritesListViewModel>()

    private val adapter by lazy {
        CitiesListAdapter(
            context = requireContext(),
            onItemCityClicked = {
                findNavController().navigate(FavoritesListFragmentDirections.actionFragmentFavoritesListToFragmentWeatherInfo(it))
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