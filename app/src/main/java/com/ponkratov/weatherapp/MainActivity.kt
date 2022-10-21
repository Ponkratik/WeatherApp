package com.ponkratov.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ponkratov.weatherapp.domain.usecase.GetThemeCodeUseCase
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val getThemeCodeUseCase by inject<GetThemeCodeUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTheme(getThemeCodeUseCase())

        val nestedController =
            (supportFragmentManager.findFragmentById(R.id.page_container) as NavHostFragment)
                .navController
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setupWithNavController(nestedController)

        nestedController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_weather_info -> navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE
            }
        }
    }

    private fun setTheme(themeCode: String) {
        AppCompatDelegate.setDefaultNightMode(
            when (themeCode) {
                "day" -> AppCompatDelegate.MODE_NIGHT_NO
                "night" -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }
}