package com.ivanespitia.androidutils.extensions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


// Alert UI

@Suppress("UNUSED")
fun AppCompatActivity.shortToast(value: String?) {
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}

@Suppress("UNUSED")
fun AppCompatActivity.longToast(value: String?) {
    Toast.makeText(this, value, Toast.LENGTH_LONG).show()
}

/**
 * Open simple dialog alert
 * @param title
 * @param message
 * @param okLabel
 */
@Suppress("UNUSED")
fun AppCompatActivity.showAlert(
    title: String?,
    message: String?,
    okLabel: String = "OK"
) {
    val dialog = AlertDialog.Builder(this)
    dialog.setTitle(title)
    dialog.setMessage(message)
    dialog.setPositiveButton(okLabel) { d, _ ->
        d.cancel()
    }
    if (!this.isFinishing)
        dialog.show()
}

/**
 * Open Question alert
 * @param title
 * @param message
 * @param okLabel
 * @param cancelLabel
 */
@Suppress("UNUSED")
fun AppCompatActivity.showQuestionAlert(
    title: String?,
    message: String?,
    okLabel: String = "OK",
    cancelLabel: String = "Cancel",
    result: (Boolean) -> Unit
) {
    val dialog = AlertDialog.Builder(this)
    dialog.setTitle(title)
    dialog.setMessage(message)
    dialog.setPositiveButton(okLabel) { d, _ ->
        d.cancel()
        result(true)
    }
    dialog.setNegativeButton(cancelLabel) { d, _ ->
        d.cancel()
        result(false)
    }
    if (!this.isFinishing)
        dialog.show()
}

/**
 * Open alert with options to pick
 * @param title
 * @param items
 */
@Suppress("UNUSED")
fun AppCompatActivity.showItemsDialog(
    title: String?,
    items: MutableList<CharSequence>,
    selected: (CharSequence) -> Unit
) {
    val dialog = AlertDialog.Builder(this)
    dialog.setTitle(title)
    dialog.setItems(items.toTypedArray()) { d, which ->
        d.cancel()
        selected(items[which])
    }
    if (!this.isFinishing)
        dialog.show()
}

// Intents

/**
 * Open new activity (optional you can put extras and set single task)
 * @param clazz
 * @param extras
 * @param singleTask
 */
@Suppress("UNUSED")
fun AppCompatActivity.launchIntent(
    clazz: Class<out AppCompatActivity>,
    extras: MutableMap<String, String?> = mutableMapOf(),
    singleTask: Boolean = false) {
    if (!this.isFinishing) {
        try {
            val intent = Intent(this, clazz)

            for ((k, v) in extras) {
                intent.putExtra(k, v)
            }

            if (singleTask)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

            this.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
}

/**
 * Open email default application in editor mode
 * @param email
 * @param subject
 * @param body
 */
fun AppCompatActivity.launchEmail(
    email: String?,
    subject: String?,
    body: String?) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        val data = Uri.parse("mailto:?subject=$subject&body=$body&to=$email")
        intent.data = data
        this.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

/**
 * Open Dial phone by phone number
 * @param phone number
 */
fun AppCompatActivity.launchDial(
    phone: String?
) {
    try {
        val phoneIntent = Intent(
            Intent.ACTION_DIAL,
            Uri.fromParts(
                "tel",
                phone,
                null
            )
        )
        this.startActivity(phoneIntent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

/**
 * Set title of action bar activity
 * @param res res id of string label
 * @param enableBackArrow set if you want to see back arrow
 */
@Suppress("UNUSED")
fun AppCompatActivity.setBarTitle(
    res: Int? = null,
    enableBackArrow: Boolean = false) {
    this.supportActionBar?.let { actionBar ->
        res?.let { i ->
            actionBar.setTitle(i)
        }
        if (enableBackArrow) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }
}

// Interaction

/**
 * Instantiate new fragment by class
 * @param clazz
 * @param bundle (optional)
 * @return Fragment
 */
@Suppress("UNUSED")
fun AppCompatActivity.fragment(clazz: Class<out Fragment>, bundle: Bundle? = null): Fragment {
    val fragment = this.supportFragmentManager.fragmentFactory.instantiate(classLoader, clazz.name)
    fragment.arguments = bundle
    return fragment
}