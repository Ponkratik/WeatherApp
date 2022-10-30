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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSettingsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            when (viewModel.onInitGetTheme()) {
                ThemeCode.THEME_CODE_DAY -> {
                    radioNightmodeOff.isChecked = true
                }
                ThemeCode.THEME_CODE_NIGHT -> {
                    radioNightmodeOn.isChecked = true
                }
                else -> {
                    radioNightmodeSystem.isChecked = true
                }
            }

            when (viewModel.onInitGetLanguage()) {
                LanguageCode.LANGUAGE_CODE_RU -> {
                    radioLanguageRu.isChecked = true
                }
                else -> {
                    radioLanguageEn.isChecked = true
                }
            }

            radioNightmodeOn.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    viewModel.onThemeChecked(ThemeCode.THEME_CODE_NIGHT)
                    setTheme(ThemeCode.THEME_CODE_NIGHT)
                }
            }

            radioNightmodeOff.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    viewModel.onThemeChecked(ThemeCode.THEME_CODE_DAY)
                    setTheme(ThemeCode.THEME_CODE_DAY)
                }
            }

            radioNightmodeSystem.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    viewModel.onThemeChecked(ThemeCode.THEME_CODE_SYSTEM)
                    setTheme(ThemeCode.THEME_CODE_SYSTEM)
                }
            }

            radioLanguageEn.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    viewModel.onLanguageChecked(LanguageCode.LANGUAGE_CODE_EN)
                    changeLanguage()
                }
            }

            radioLanguageRu.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    viewModel.onLanguageChecked(LanguageCode.LANGUAGE_CODE_RU)
                    changeLanguage()
                }
            }
        }
    }

    private fun changeLanguage() {
        requireActivity().recreate()
    }

    private fun setTheme(themeCode: ThemeCode) {
        AppCompatDelegate.setDefaultNightMode(
            when (themeCode) {
                ThemeCode.THEME_CODE_DAY -> AppCompatDelegate.MODE_NIGHT_NO
                ThemeCode.THEME_CODE_NIGHT -> AppCompatDelegate.MODE_NIGHT_YES
                ThemeCode.THEME_CODE_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}