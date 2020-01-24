package com.ivanespitia.androidutils.extensions

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

/**
 * inflate view by a viewgroup
 * @param layoutRes
 */
@Suppress("UNUSED")
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

/**
 * Set text inside a editText
 * @param value
 */
@Suppress("UNUSED")
fun EditText.text(value: String?) {
    value.let {
        this.setText(it, TextView.BufferType.EDITABLE)
    }
}

/**
 * Set HTML format text inside textview
 * @param value HTML
 */
@Suppress("UNUSED")
fun TextView.textHtml(value: String?) {
    value?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text = Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            this.text = Html.fromHtml(value)
        }
    }
}