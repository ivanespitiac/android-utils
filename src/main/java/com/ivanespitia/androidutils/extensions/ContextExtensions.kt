package com.ivanespitia.androidutils.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator


/**
 * Check if internet access connection is available
 */
@Suppress("UNUSED")
fun Context.networkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw      = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION")
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return nwInfo.isConnected
    }
}

/**
 * Make device vibrate
 * @param time milli seconds
 */
@Suppress("UNUSED")
fun Context.vibrate(time: Long) {
    val vibrate = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrate.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrate.vibrate(time)
    }
}

/**
 * Copy text
 */
@Suppress("UNUSED")
fun Context.copyText(key: String, value: String) {
    val clip = ClipData.newPlainText(key, value)
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    clipboard?.setPrimaryClip(clip)
}