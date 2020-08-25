package com.sahla.sahla.data.manager.preference

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.sahla.sahla.data.local.PreferenceHelper
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class LocaleManager @Inject constructor(var preferenceHelper: PreferenceHelper) {

    fun setLocale(c: Context) {
        changeLanguage(context = c, language = getLanguage())
    }

    fun changeLanguage(context: Context, language: String? = null): Context {
        preferenceHelper.changeApplicationLanguage(language)
        return updateResources(context)
    }

    fun getLanguage(): String = preferenceHelper.selectedLanguage()

    fun updateResources(context: Context): Context {
        val c: Context
        Timber.i("application language: ${getLanguage()}")
        val locale = Locale(getLanguage())
        Locale.setDefault(locale)
        val res: Resources = context.resources
        val config = Configuration(res.configuration)

        config.setLocale(locale)
        res.updateConfiguration(config, res.displayMetrics)
        c = context.createConfigurationContext(config)
        return c
    }
}