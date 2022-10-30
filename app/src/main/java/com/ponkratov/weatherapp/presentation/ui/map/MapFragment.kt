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
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.ponkratov.weatherapp.R
import com.ponkratov.weatherapp.data.service.LocationService
import com.ponkratov.weatherapp.databinding.FragmentMapBinding
import com.ponkratov.weatherapp.domain.model.City
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var googleMap: GoogleMap? = null
    private var locationListener: LocationSource.OnLocationChangedListener? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isEnabled ->
        setLocationEnabled(isEnabled)
        if (isEnabled) {
            observeLocationChanges()
        }
    }

    private val viewModel by viewModel<MapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMapBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        initGoogleMap { map ->
            viewModel
                .lceFlow
                .onEach { cities ->
                    cities.forEach { city ->
                        val marker = map.addMarker(
                            MarkerOptions()
                                .draggable(false)
                                .position(LatLng(city.latitude, city.longitude))
                                .title(city.name)
                        )

                        marker?.tag = city
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

        binding.fragmentMap.onCreate(savedInstanceState)

        viewModel.databaseFlow.tryEmit(Unit)
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

    @SuppressLint("PotentialBehaviorOverride")
    private fun initGoogleMap(action: (GoogleMap) -> Unit) {
        binding.fragmentMap.getMapAsync { map ->
            googleMap = map.apply {
                initMapStyle()

                uiSettings.isCompassEnabled = true
                uiSettings.isZoomControlsEnabled = true

                setLocationSource(object : LocationSource {
                    override fun activate(listener: LocationSource.OnLocationChangedListener) {
                        locationListener = listener
                    }

                    override fun deactivate() {
                        locationListener = null
                    }
                })

                map.setOnMarkerClickListener { settedMarker ->
                    findNavController().navigate(
                        MapFragmentDirections.actionFragmentMapToFragmentWeatherInfo(
                            settedMarker.tag as City
                        )
                    )
                    true
                }
            }

            val hasLocationPermission =
                hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            setLocationEnabled(hasLocationPermission)

            if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                observeLocationChanges()
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            action(map)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setLocationEnabled(enabled: Boolean) {
        googleMap?.isMyLocationEnabled = enabled
        googleMap?.uiSettings?.isMyLocationButtonEnabled = enabled
    }

    private fun observeLocationChanges() {
        viewModel
            .startLocationFlow
            .onEach(::moveCameraToLocation)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel
            .locationFlow
            .onEach {
                locationListener?.onLocationChanged(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun moveCameraToLocation(location: Location) {
        val current = LatLng(location.latitude, location.longitude)
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(current, DEFAULT_CAMERA_ZOOM)
        )
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun GoogleMap.initMapStyle() {
        if (viewModel.isNightMode()) {
            setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style_json)
            )
        }
    }

    companion object {
        private const val DEFAULT_CAMERA_ZOOM = 17f
    }
}