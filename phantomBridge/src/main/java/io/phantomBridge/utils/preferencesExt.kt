package io.phantomBridge.utils

import android.content.SharedPreferences
import android.util.Log

fun SharedPreferences.getStringIfExists(extra: String) = if (this.contains(extra)) {
    this.getString(extra, "") ?: ""
} else {
    Log.e("SharedPreferencesUtils", "Watch out, your $extra not saved")
    ""
}

fun SharedPreferences.putAllStrings(vararg pair: Pair<String?, String?>) {
    this.edit().apply {
        pair.forEach { putString(it.first, it.second) }
    }.apply()
}

fun SharedPreferences.clear() {
    this.edit().clear().apply()
}