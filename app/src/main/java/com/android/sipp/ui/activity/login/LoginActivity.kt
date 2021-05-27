package com.android.sipp.ui.activity.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.sipp.R
import com.android.sipp.databinding.ActivityLoginBinding
import com.android.sipp.model.Users
import com.android.sipp.preference.PreferenceManager
import com.android.sipp.ui.activity.main.MainActivity
import com.android.sipp.utils.Utils.hideLoading
import com.android.sipp.utils.Utils.showLoading
import com.android.sipp.utils.Utils.showMessage
import com.android.sipp.utils.Utils.start
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var email: String? = null
    private var password: String? = null
    private var userData: Users? = null
    private var errorMessage: String? = null

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var user: FirebaseUser
    private lateinit var b: ActivityLoginBinding
    private lateinit var pref: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiate()
        setClickListener()
        checkData()
    }

    private fun initiate() {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        pref = PreferenceManager(this)
    }

    private fun setClickListener() {
        b.btnBack.setOnClickListener(this)
        b.btnLogin.setOnClickListener(this)
    }

    private fun checkData() {
        b.btnLogin.isEnabled = false
        b.etEmail.addTextChangedListener(textWatcher)
        b.etPassword.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            b.btnLogin.isEnabled =
                b.etEmail.text.toString().isNotEmpty() && b.etPassword.text.toString().isNotEmpty()
            email = b.etEmail.text.toString()
            password = b.etPassword.text.toString()
        }
    }

    private fun checkEmailInUser() {
        loginViewModel.checkEmailInUser(email!!, password!!)
        loginViewModel.loading.observe(this, {
            if (it == true){
                showLoading(this, b.progressbar)
            } else {
                hideLoading(this, b.progressbar)
            }
        })
        loginViewModel.successSignIn.observe(this, {
            if (it == true){
                start(this, MainActivity::class.java)
                finish()
            }
        })
        loginViewModel.userData.observe(this, {
            userData = it
        })
        loginViewModel.savePreference.observe(this, { saveData ->
            if (saveData == true){
                userData?.let { it -> pref.saveUserData(it) }
            }
        })
        loginViewModel.errorMessage.observe(this, { errorMessage ->
            loginViewModel.showMessage.observe(this, { showMessage ->
                if (showMessage == true) {
                    showMessage(b.root, errorMessage)
                }
            })
        })

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> finish()
            R.id.btn_login -> checkEmailInUser()
        }
    }
}