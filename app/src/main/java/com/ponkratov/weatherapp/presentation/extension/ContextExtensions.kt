package com.ponkratov.weatherapp.presentation.extension

import android.content.Context
import android.content.res.Configuration
import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import java.util.*

fun Context.applySelectedAppLanguage(languageCode: LanguageCode): Context {
    val newConfig = Configuration(resources.configuration)
    Locale.setDefault(languageCode.languageCode)
    newConfig.setLocale(languageCode.languageCode)

    return createConfigurationContext(newConfig)
}