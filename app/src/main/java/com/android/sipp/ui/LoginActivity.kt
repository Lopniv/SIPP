package com.android.sipp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.sipp.R
import com.android.sipp.databinding.ActivityLoginBinding
import com.android.sipp.utils.Utils
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_INDUSTRY
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_USER
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_EMAIL
import com.android.sipp.utils.Utils.showDialog
import com.android.sipp.utils.Utils.showMessage
import com.android.sipp.utils.Utils.start
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var email: String? = null
    private var password: String? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var user: FirebaseUser
    private lateinit var b: ActivityLoginBinding

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
        showDialog(this, true)
        firestore.collection(COLLECTION_USER)
            .whereEqualTo(FIELD_EMAIL, email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.result?.isEmpty == true){
                        checkEmailInIndustry()
                    } else {
                        requestSignIn()
                    }
                } else {
                    showDialog(this, false)
                    task.exception?.message?.let { message -> showMessage(b.root, message) }
                }
            }
    }

    private fun checkEmailInIndustry() {
        firestore.collection(COLLECTION_INDUSTRY)
            .whereEqualTo(FIELD_EMAIL, email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.result?.isEmpty == true){
                        checkEmailInDriver()
                    } else {
                        requestSignIn()
                    }
                } else {
                    showDialog(this, false)
                    task.exception?.message?.let { message -> showMessage(b.root, message) }
                }
            }
    }

    private fun checkEmailInDriver() {

    }

    private fun requestSignIn() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user.isEmailVerified) {
                        pref.saveAdminData(admin)
                        showDialog(this, false)
                        start(this, MainActivity::class.java)
                        finish()
                    } else {
                        auth.signOut()
                        showDialog(this, false)
                        showMessage(b.root, "Email kamu belum terverifikasi")
                    }
                } else {
                    showDialog(this, false)
                    task.exception?.message?.let { message -> showMessage(b.root, message) }
                }
            }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> finish()
            R.id.btn_login -> checkEmailInUser()
        }
    }
}