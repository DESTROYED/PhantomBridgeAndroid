package io.phantomBridge.utils

import android.content.SharedPreferences

fun SharedPreferences.getStringIfExists(extra: String) = if (this.contains(extra)) {
    this.getString(extra, "")
} else {
    null
}

fun SharedPreferences.putAllStrings(vararg pair: Pair<String?, String?>) {
    this.edit().apply {
        pair.forEach { putString(it.first, it.second) }
    }.apply()
}