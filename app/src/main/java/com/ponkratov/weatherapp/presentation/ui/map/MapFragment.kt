package com.ponkratov.weatherapp.presentation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMapLoad
import com.ponkratov.weatherapp.R
import com.ponkratov.weatherapp.databinding.FragmentMapBinding
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.presentation.ui.weatherinfo.WeatherInfoViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment: Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var googleMap: GoogleMap? = null
    private var locationListener: LocationSource.OnLocationChangedListener? = null

    private val locationService by lazy {
        LocationService(requireContext())
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isEnabled ->
        setLocationEnabled(isEnabled)
        if (isEnabled) {
            viewLifecycleOwner.lifecycleScope.launch {
                locationService.getLocation()?.let(::moveCameraToLocation)
            }

            locationService
                .locationFlow
                .onEach { location ->
                    locationListener?.onLocationChanged(location)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private val viewModel by viewModel<MapViewModel>()

    private val weatherInfoViewModel by sharedViewModel<WeatherInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMapBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    @SuppressLint("MissingPermission", "PotentialBehaviorOverride")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        binding.fragmentMap.getMapAsync { map ->
            googleMap = map.apply {

                uiSettings.isCompassEnabled = true
                uiSettings.isZoomControlsEnabled = true
                uiSettings.isMyLocationButtonEnabled = true

                /*viewModel
                    .lceFlow
                    .onEach {
                        it.forEach { city ->
                            googleMap?.awaitMapLoad()
                            googleMap?.addMarker(MarkerOptions()
                                .draggable(false)
                                .position(LatLng(city.latitude, city.longitude)))
                        }
                    }
                    .launchIn(viewLifecycleOwner.lifecycleScope)*/

                isMyLocationEnabled = hasLocationPermission()

                setLocationSource(object : LocationSource {
                    override fun activate(listener: LocationSource.OnLocationChangedListener) {
                        locationListener = listener
                    }

                    override fun deactivate() {
                        locationListener = null
                    }
                })
            }
        }

        viewModel.databaseFlow.tryEmit(Unit)

        viewModel
            .lceFlow
            .onEach { it ->
                it.forEach { city ->
                    binding.fragmentMap.getMapAsync { map ->
                        val marker = map.addMarker(MarkerOptions()
                            .draggable(false)
                            .position(LatLng(city.latitude, city.longitude))
                            .title(city.name))

                        marker?.tag = city

                        map.setOnMarkerClickListener { settedMarker ->
                            weatherInfoViewModel.cityFlow.tryEmit(settedMarker.tag as City)
                            findNavController().navigate(R.id.action_fragment_map_to_fragment_weather_info)
                            true
                        }
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        with(binding) {
            binding.fragmentMap.onCreate(savedInstanceState)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.fragmentMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.fragmentMap.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.fragmentMap.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.fragmentMap.onDestroy()
        googleMap = null
        _binding = null
    }

    @SuppressLint("MissingPermission")
    private fun setLocationEnabled(enabled: Boolean) {
        googleMap?.isMyLocationEnabled = enabled
        googleMap?.uiSettings?.isMyLocationButtonEnabled = enabled
    }

    private fun moveCameraToLocation(location: Location) {
        val current = LatLng(location.latitude, location.longitude)
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(current, DEFAULT_CAMERA_ZOOM)
        )
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val DEFAULT_CAMERA_ZOOM = 17f
    }
}