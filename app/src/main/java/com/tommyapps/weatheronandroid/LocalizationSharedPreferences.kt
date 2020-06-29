package com.tommyapps.weatheronandroid

import android.content.Context
import android.content.SharedPreferences

class LocalizationSharedPreferences(val context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences("localization", Context.MODE_PRIVATE)

    fun save(key: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(key, text)

        editor.apply()
    }

    fun getValueString(key: String): String? {

        return sharedPref.getString(key, null)

    }

    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        editor.clear()
        editor.apply()
    }




}