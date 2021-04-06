package com.android.sipp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.android.sipp.R

class SplashscreenActivity : AppCompatActivity() {

    companion object {
        private const val TIME_LENGTH = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        goToApp()
    }

    private fun goToApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            IntroActivity.Utils.start(this)
        }, TIME_LENGTH.toLong())
    }
}