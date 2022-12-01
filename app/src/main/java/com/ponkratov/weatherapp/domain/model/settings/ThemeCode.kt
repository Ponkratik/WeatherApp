package com.ponkratov.weatherapp.domain.model.settings

enum class ThemeCode(val themeCode: String) {
    THEME_CODE_DAY("day"),
    THEME_CODE_NIGHT("night"),
    THEME_CODE_SYSTEM("system");

    companion object {
        val THEME_CODE_DEFAULT = THEME_CODE_SYSTEM
    }
}