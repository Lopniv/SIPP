package com.android.sipp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object Utils {
    object FirestoreKeys {
        const val COLLECTION_USER = "User"
        const val COLLECTION_ADMIN = "Admin"
        const val COLLECTION_INDUSTRY = "Industry"
        const val FIELD_FIRST_NAME = "firstName"
        const val FIELD_LAST_NAME = "lastName"
        const val FIELD_EMAIL = "email"
        const val FIELD_PHONE = "phone"
    }

    object Keys {
        const val PASSWORD = "password"
        const val CATEGORY = "CATEGORY"
        const val DEFAULT = 0
        const val VALUE_PERSONAL = 1
        const val VALUE_INDUSTRY = 2
    }

    fun showLoading(activity: Activity, progressBar: View) {
        progressBar.visibility = VISIBLE
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun hideLoading(activity: Activity, progressBar: View) {
        progressBar.visibility = INVISIBLE
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}