package com.android.sipp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.android.sipp.R
import com.android.sipp.preference.Constants.KEY_USER_ID
import com.android.sipp.preference.PreferenceManager
import com.android.sipp.ui.activity.intro.IntroActivity
import com.android.sipp.ui.activity.main.MainActivity
import com.android.sipp.utils.Utils.start

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var pref: PreferenceManager

    companion object {
        private const val TIME_LENGTH = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        initiate()
        goToApp()
    }

    private fun initiate() {
        pref = PreferenceManager(this)
    }

    private fun goToApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (pref.getUserId(KEY_USER_ID) == null){
                start(this, IntroActivity::class.java)
            } else {
                start(this, MainActivity::class.java)
            }
        }, TIME_LENGTH.toLong())
    }
}