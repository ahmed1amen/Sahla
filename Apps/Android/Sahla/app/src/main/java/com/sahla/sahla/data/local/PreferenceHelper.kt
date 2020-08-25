package com.sahla.sahla.data.local

import android.content.Context
import com.sahla.sahla.utils.Constants
import com.sahla.sahla.utils.Enums
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

/**
 * this class will help to retrieve the current value for saved data with convenience named methods
 */
class PreferenceHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferenceUtils: PreferenceUtils
) {

    fun removeUserData() = preferenceUtils.clearUserData()

    fun selectedLanguage(): String =
        preferenceUtils.ConvertType<String>()
            .convert(Constants.SELECTED_LANGUAGE_KEY, String::class.java)
            ?: getDefaultLanguage().lang


    private fun getDefaultLanguage(): Enums.Languages =
        if (Locale.getDefault().language == Enums.Languages.ARABIC.lang) Enums.Languages.ARABIC else Enums.Languages.ENGLISH

    /**
     * @param language in the future when the app supports multiple languages use this parameter instead of passing only two values
     */
    fun changeApplicationLanguage(language: String? = null) {
        preferenceUtils.saveData(
            Constants.SELECTED_LANGUAGE_KEY,
            if (selectedLanguage() == Enums.Languages.ARABIC.lang) {
                Timber.i("Selected New Language To Arabic")
                Enums.Languages.ENGLISH.lang
            } else {
                Timber.i("Selected New Language To English")
                Enums.Languages.ARABIC.lang
            }
        )
    }

    fun formatCurrency(total: Double): String {
        val numberFormatter = NumberFormat.getCurrencyInstance(Locale(selectedLanguage()))
        numberFormatter.currency = Currency.getInstance("EGP")
        return numberFormatter.format(total)
    }
}