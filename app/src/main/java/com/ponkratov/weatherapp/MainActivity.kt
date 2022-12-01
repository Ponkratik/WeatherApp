package com.ponkratov.weatherapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ponkratov.weatherapp.domain.model.settings.ThemeCode
import com.ponkratov.weatherapp.domain.service.LanguageService
import com.ponkratov.weatherapp.domain.service.ThemeService
import com.ponkratov.weatherapp.presentation.extension.applySelectedAppLanguage
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val themeService by inject<ThemeService>()

    private val languageService by inject<LanguageService>()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.applySelectedAppLanguage(languageService.language))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialNightMode()
    }

    private fun initialNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            when (themeService.themeCode) {
                ThemeCode.THEME_CODE_NIGHT -> AppCompatDelegate.MODE_NIGHT_YES
                ThemeCode.THEME_CODE_DAY -> AppCompatDelegate.MODE_NIGHT_NO
                ThemeCode.THEME_CODE_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }
}