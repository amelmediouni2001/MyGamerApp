package tn.esprit.gamerappp2.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

const val PREF_NAME = "user"
const val KEY_EMAIL = "email"
const val KEY_PASSWORD = "password"

fun getSharedPref(context: Context): SharedPreferences {
    return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
}

fun saveUserData(context: Context, email: String, password: String) {
    getSharedPref(context).edit {
        putString(KEY_EMAIL, email)
        putString(KEY_PASSWORD, password)
    }
}

fun clearUserData(context: Context) {
    getSharedPref(context).edit {
        clear()
    }
}

fun getEmail(context: Context): String {
    return getSharedPref(context).getString(KEY_EMAIL, "").orEmpty()
}

fun getPassword(context: Context): String {  // NEW: Get password
    return getSharedPref(context).getString(KEY_PASSWORD, "").orEmpty()
}

fun loadUserData(context: Context): Pair<String, String>? {  // NEW: Load if exists
    val email = getEmail(context)
    val password = getPassword(context)
    return if (email.isNotEmpty() && password.isNotEmpty()) Pair(email, password) else null
}