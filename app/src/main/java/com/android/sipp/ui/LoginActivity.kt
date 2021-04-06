package com.android.sipp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.sipp.R
import com.android.sipp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)
    }
}