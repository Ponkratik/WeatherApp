package com.ponkratov.weatherapp.domain.model.settings

import java.util.Locale

enum class LanguageCode(val languageCode: Locale) {
    LANGUAGE_CODE_EN(Locale.US),
    LANGUAGE_CODE_RU(Locale("ru"));

    companion object {
        val LANGUAGE_CODE_DEFAULT = LANGUAGE_CODE_EN
    }
}