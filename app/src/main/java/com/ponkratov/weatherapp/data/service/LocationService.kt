package com.ponkratov.weatherapp.data.service

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("MissingPermission")
class LocationService(context: Context) {

    private var currentLocation: Location? = null

    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationFlow: Flow<Location> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { trySend(it) }
            }
        }

        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATE_INTERVAL
        ).build()

        locationClient.requestLocationUpdates(request, callback, Looper.getMainLooper())

        awaitClose {
            locationClient.removeLocationUpdates(callback)
        }
    }

    suspend fun getLocation(): Location? {
        return currentLocation ?: lastKnownLocation()
    }

    private suspend fun lastKnownLocation(): Location? = suspendCoroutine { cont ->
        locationClient.lastLocation
            .addOnSuccessListener { location ->
                cont.resume(location)
            }
            .addOnCanceledListener {
                cont.resume(null)
            }
            .addOnFailureListener {
                cont.resume(null)
            }
    }

    companion object {
        private const val LOCATION_UPDATE_INTERVAL = 5000L
    }
}