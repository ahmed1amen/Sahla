package com.sahla.sahla.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

/**
 * this class will help to set and get data from shared preference local storage
 */

class PreferenceUtils @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveData(key: String, value: Any) {
        with(sharedPreferences.edit()) {
            putString(key, Gson().toJson(value))
            apply()
        }
    }

    inner class ConvertType<T> {
        private fun getSavedData(key: String): String? {
            return with(sharedPreferences) {
                getString(key, null)
            }
        }

        fun convert(key: String, type: Class<T>): T? {
            return Gson().fromJson(getSavedData(key), type)
        }
    }

    fun clearUserData() {
        with(sharedPreferences.edit()) {
            apply()
        }
    }
}