package com.android.sipp.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.android.sipp.R
import com.android.sipp.ui.IntroActivity
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
        const val VALUE_PERSONAL = "personal"
        const val VALUE_INDUSTRY = "industry"
    }

    fun showDialog(activity: Activity, isShow: Boolean) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_loading)
        if (isShow){
            dialog.show()
        } else {
            dialog.dismiss()
        }
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

    fun start(context: Context, activity: Class<*>) {
        val intent = Intent(context, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}