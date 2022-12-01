package com.ponkratov.weatherapp.domain.service

import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import kotlinx.coroutines.flow.Flow

interface LanguageService {

    var language: LanguageCode
    val languageFlow: Flow<LanguageCode>
}