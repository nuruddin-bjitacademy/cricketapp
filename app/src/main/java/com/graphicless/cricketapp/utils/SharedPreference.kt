package com.graphicless.cricketapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreference() {
    private val context = MyApplication.instance
    private val fileName = "SharedPreference"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    fun isContain(KEY_NAME: String): Boolean{
        return sharedPreferences.contains(KEY_NAME)
    }

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(KEY_NAME, status)
        editor.apply()
    }

    fun getString(KEY_NAME: String): String? {
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun getInt(KEY_NAME: String): Int {
        return sharedPreferences.getInt(KEY_NAME, 0)
    }

    fun getBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(KEY_NAME, defaultValue)
    }

    fun removeAll() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun remove(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }
}