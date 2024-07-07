package com.example.wordlemix

import android.content.Context
import android.content.SharedPreferences

class PlayerPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_USERNAME = "username"
    }

    fun saveUsername(username: String) {
        preferences.edit().putString(KEY_USERNAME, username).apply()
    }

    fun getUsername(): String? {
        return preferences.getString(KEY_USERNAME, null)
    }
}