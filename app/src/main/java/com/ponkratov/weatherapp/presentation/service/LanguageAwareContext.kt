package com.ponkratov.weatherapp.presentation.service

import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import com.ponkratov.weatherapp.presentation.extension.applySelectedAppLanguage

class LanguageAwareContext(base: Context, application: Application) : ContextWrapper(base) {

    var appLanguageCode: LanguageCode = getDefaultLanguageCode()
        set(value) {
            field = value
            localizedResources = getLocalizedResources(value)
        }

    private var localizedResources = getLocalizedResources(appLanguageCode)

    init {
        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                localizedResources = getLocalizedResources(appLanguageCode)
            }

            override fun onLowMemory() {}
        })
    }

    override fun getResources(): Resources = localizedResources

    private fun getLocalizedResources(languageCode: LanguageCode): Resources {
        return baseContext.applySelectedAppLanguage(languageCode).resources
    }

    private fun getDefaultLanguageCode() = LanguageCode.LANGUAGE_CODE_DEFAULT

}