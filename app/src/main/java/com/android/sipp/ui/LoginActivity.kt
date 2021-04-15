package com.android.sipp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.sipp.R
import com.android.sipp.databinding.ActivityLoginBinding
import com.android.sipp.model.Users
import com.android.sipp.preference.PreferenceManager
import com.android.sipp.utils.Utils
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_INDUSTRY
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_USER
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_EMAIL
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_FIRST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_ID
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_LAST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_PHONE
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_TYPE
import com.android.sipp.utils.Utils.hideLoading
import com.android.sipp.utils.Utils.showDialog
import com.android.sipp.utils.Utils.showLoading
import com.android.sipp.utils.Utils.showMessage
import com.android.sipp.utils.Utils.start
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var email: String? = null
    private var password: String? = null

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
        showLoading(this, b.progressbar)
        firestore.collection(COLLECTION_USER)
            .whereEqualTo(FIELD_EMAIL, email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.result?.isEmpty == true){
                        checkEmailInIndustry()
                    } else {
                        val data = task.result?.documents?.get(0)
                        val user = Users(
                                data?.getString(FIELD_ID)!!,
                                data.getString(FIELD_FIRST_NAME)!!,
                                data.getString(FIELD_LAST_NAME)!!,
                                data.getString(FIELD_EMAIL)!!,
                                data.getString(FIELD_PHONE)!!,
                                data.getString(FIELD_TYPE)!!
                        )
                        requestSignIn(user)
                    }
                } else {
                    hideLoading(this, b.progressbar)
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
                        val data = task.result?.documents?.get(0)
                        val user = Users(
                                data?.getString(FIELD_ID)!!,
                                data.getString(FIELD_FIRST_NAME)!!,
                                data.getString(FIELD_LAST_NAME)!!,
                                data.getString(FIELD_EMAIL)!!,
                                data.getString(FIELD_PHONE)!!,
                                data.getString(FIELD_TYPE)!!
                        )
                        requestSignIn(user)
                    }
                } else {
                    hideLoading(this, b.progressbar)
                    task.exception?.message?.let { message -> showMessage(b.root, message) }
                }
            }
    }

    private fun checkEmailInDriver() {

    }

    private fun requestSignIn(userLogin: Users) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user.isEmailVerified) {
                        pref.saveUserData(userLogin)
                        hideLoading(this, b.progressbar)
                        start(this, MainActivity::class.java)
                        finish()
                    } else {
                        auth.signOut()
                        hideLoading(this, b.progressbar)
                        showMessage(b.root, "Email kamu belum terverifikasi")
                    }
                } else {
                    hideLoading(this, b.progressbar)
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