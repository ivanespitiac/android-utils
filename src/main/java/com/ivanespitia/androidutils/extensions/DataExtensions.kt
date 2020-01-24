package com.ivanespitia.androidutils.extensions

import android.content.res.Resources
import android.util.Log
import android.util.Patterns
import org.json.JSONObject

/**
 * Make a log of any data with 'LOG' TAG
 */
@Suppress("UNUSED")
fun Any?.log() {
    Log.d("LOG", "$this")
}

/**
 * Transform a Int in a Graphic DP
 */
@Suppress("UNUSED")
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()


/**
 * Remove all white spaces from string
 */
@Suppress("UNUSED")
fun String.removeSpaces(): String {
    return this.replace("\\s".toRegex(), "")
}

/**
 * Get Numbers from string
 */
@Suppress("UNUSED")
fun String.getNumbers(): String {
    var result = this
    val regex = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ :.-_"
    for (index in regex) {
        result = result.replace("$index", "", false)
    }
    return result
}

/**
 * Check if a string is an email
 */
@Suppress("UNUSED")
fun String.isEmail(): Boolean {
    return if (this.isBlank()) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}

/**
 * Verify if a String is null
 * @param key key of json value
 */
@Suppress("UNUSED")
fun JSONObject?.optStringNull(key: String): String {
    return if (this?.isNull(key) == true)
        ""
    else
        this?.optString(key) ?: ""
}