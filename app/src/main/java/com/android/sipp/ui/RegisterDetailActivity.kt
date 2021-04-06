package com.android.sipp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.sipp.R
import com.android.sipp.databinding.ActivityRegisterDetailBinding

class RegisterDetailActivity : AppCompatActivity() {

    private lateinit var b : ActivityRegisterDetailBinding

    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var password: String? = null

    companion object {
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val EMAIL = "email"
        const val PHONE = "phone"
        const val PASSWORD = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterDetailBinding.inflate(layoutInflater)
        setContentView(b.root)
        getExtra()
    }

    private fun getExtra() {
        firstName = intent.getStringExtra(FIRST_NAME)
        lastName = intent.getStringExtra(LAST_NAME)
        email = intent.getStringExtra(EMAIL)
        phone = intent.getStringExtra(PHONE)
        password = intent.getStringExtra(PASSWORD)
    }
}