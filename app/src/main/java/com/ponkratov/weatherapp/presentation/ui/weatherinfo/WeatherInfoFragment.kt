package com.ponkratov.weatherapp.presentation.ui.weatherinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ponkratov.weatherapp.databinding.FragmentWeatherInfoBinding
import com.ponkratov.weatherapp.presentation.extension.addVerticalSpace
import com.ponkratov.weatherapp.presentation.ui.weatherinfo.adapter.WeatherListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.roundToInt

class WeatherInfoFragment : Fragment() {
    private var _binding: FragmentWeatherInfoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args:WeatherInfoFragmentArgs by navArgs()

    private val viewModel by viewModel<WeatherInfoViewModel> {
        parametersOf(args.city)
    }

    private val adapter by lazy {
        WeatherListAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentWeatherInfoBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonBack.setOnClickListener {
                findNavController().navigateUp()
            }

            weatherRecyclerView.adapter = adapter
            weatherRecyclerView.addVerticalSpace()

            viewModel
                .dataFlow
                .onEach {
                    adapter.submitList(it.weatherList)
                    textviewCurrentTemperature.text = it.currentTemperature
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}