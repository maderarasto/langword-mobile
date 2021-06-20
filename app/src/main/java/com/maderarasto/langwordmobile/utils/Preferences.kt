package com.maderarasto.langwordmobile.utils

import android.content.Context
import android.content.SharedPreferences
import com.maderarasto.langwordmobile.R

class Preferences (context: Context) {
    companion object {
        private const val APP_PREF_ACCESS_TOKEN = "accessTokenPref"

        @Volatile
        private var INSTANCE: Preferences? = null
        fun getInstance(context: Context) =
            INSTANCE ?: Preferences(context).also {
                INSTANCE = it
            }
    }

    private val preferencesKey: String = context.getString(R.string.preferences_key)
    private val preferences: SharedPreferences = context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE)

    var accessToken: String
        get() = preferences.getString(APP_PREF_ACCESS_TOKEN, "").toString()
        set(value) = preferences.edit().putString(APP_PREF_ACCESS_TOKEN, value).apply()
}