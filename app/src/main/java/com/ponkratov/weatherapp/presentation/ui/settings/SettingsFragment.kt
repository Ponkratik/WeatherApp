package com.ponkratov.weatherapp.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.ponkratov.weatherapp.databinding.FragmentSettingsBinding
import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import com.ponkratov.weatherapp.domain.model.settings.ThemeCode
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return FragmentSettingsBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            when (viewModel.currentThemeCode) {
                ThemeCode.THEME_CODE_DAY -> radioNightmodeOff
                ThemeCode.THEME_CODE_NIGHT -> radioNightmodeOn
                ThemeCode.THEME_CODE_SYSTEM -> radioNightmodeSystem
            }.isChecked = true

            when (viewModel.currentLanguageCode) {
                LanguageCode.LANGUAGE_CODE_RU -> radioLanguageRu
                LanguageCode.LANGUAGE_CODE_EN -> radioLanguageEn
            }.isChecked = true

            radiogroupNightmode.setOnCheckedChangeListener { _, radioId ->
                val (prefsCode, systemCode) = when (radioId) {
                    radioNightmodeOn.id -> ThemeCode.THEME_CODE_NIGHT to AppCompatDelegate.MODE_NIGHT_YES
                    radioNightmodeOff.id -> ThemeCode.THEME_CODE_DAY to AppCompatDelegate.MODE_NIGHT_NO
                    radioNightmodeSystem.id -> ThemeCode.THEME_CODE_SYSTEM to AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    else -> ThemeCode.THEME_CODE_SYSTEM to AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }

                viewModel.currentThemeCode = prefsCode
                AppCompatDelegate.setDefaultNightMode(systemCode)
            }

            radiogroupLanguage.setOnCheckedChangeListener { _, radioId ->
                val languageCode = when (radioId) {
                    radioLanguageEn.id -> LanguageCode.LANGUAGE_CODE_EN
                    radioLanguageRu.id -> LanguageCode.LANGUAGE_CODE_RU
                    else -> LanguageCode.LANGUAGE_CODE_DEFAULT
                }

                viewModel.currentLanguageCode = languageCode

                activity?.recreate()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}