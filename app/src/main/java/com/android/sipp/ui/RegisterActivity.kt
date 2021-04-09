package com.android.sipp.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.sipp.R
import com.android.sipp.databinding.ActivityRegisterBinding
import com.android.sipp.model.Users
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_INDUSTRY
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_USER
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_EMAIL
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_FIRST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_LAST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_PHONE
import com.android.sipp.utils.Utils.Keys.CATEGORY
import com.android.sipp.utils.Utils.Keys.DEFAULT
import com.android.sipp.utils.Utils.Keys.PASSWORD
import com.android.sipp.utils.Utils.Keys.VALUE_INDUSTRY
import com.android.sipp.utils.Utils.Keys.VALUE_PERSONAL
import com.android.sipp.utils.Utils.hideLoading
import com.android.sipp.utils.Utils.showLoading
import com.android.sipp.utils.Utils.showMessage
import com.android.sipp.utils.Utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var b : ActivityRegisterBinding

    private var category : Int? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var password: String? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var user: FirebaseUser
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiateFirebase()
        setClickListener()
        getExtra()
        setView()
        checkData()
    }

    private fun initiateFirebase() {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        //val storage = FirebaseStorage.getInstance()
        //storageReference = storage.reference.child(REFERENCE_IMAGE_PROFILE_ADMINS)
    }

    private fun setClickListener() {
        b.btnRegister.setOnClickListener(this)
        b.btnBack.setOnClickListener(this)
    }

    private fun getExtra() {
        category = intent.getIntExtra(CATEGORY, DEFAULT)
    }

    private fun setView() {
        if (category == VALUE_PERSONAL){
            b.btnRegister.text = getString(R.string.register)
        }
    }

    private fun checkData() {
        b.btnRegister.isEnabled = false
        b.etEmail.addTextChangedListener(textWatcher)
        b.etFirstName.addTextChangedListener(textWatcher)
        b.etLastName.addTextChangedListener(textWatcher)
        b.etPhone.addTextChangedListener(textWatcher)
        b.etPassword.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            b.btnRegister.isEnabled =
                b.etEmail.text.toString().isNotEmpty() &&
                b.etFirstName.text.toString().isNotEmpty() &&
                b.etLastName.text.toString().isNotEmpty() &&
                b.etPhone.text.toString().isNotEmpty() &&
                b.etPassword.text.toString().isNotEmpty()
            firstName = b.etFirstName.text.toString()
            lastName = b.etLastName.text.toString()
            email = b.etEmail.text.toString()
            phone = b.etPhone.text.toString()
            password = b.etPassword.text.toString()
        }
    }

    private fun register() {
        if (category == VALUE_PERSONAL){
            checkEmailInUser()
        } else if (category == VALUE_INDUSTRY) {
            val detailRegister = Intent(this, RegisterDetailActivity::class.java)
            detailRegister.putExtra(FIELD_FIRST_NAME, firstName)
            detailRegister.putExtra(FIELD_LAST_NAME, lastName)
            detailRegister.putExtra(FIELD_EMAIL, email)
            detailRegister.putExtra(FIELD_PHONE, phone)
            detailRegister.putExtra(PASSWORD, password)
            startActivity(detailRegister)
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
                        hideLoading(this, b.progressbar)
                        showMessage(b.root, "Maaf, email sudah digunakan")
                    }
                } else {
                    hideLoading(this, b.progressbar)
                    task.exception?.message?.let { message -> showMessage(b.root, message) }
                }
            }
    }

    private fun checkEmailInIndustry() {
        showLoading(this, b.progressbar)
        firestore.collection(COLLECTION_INDUSTRY)
            .whereEqualTo(FIELD_EMAIL, email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.result?.isEmpty == true){
                        requestCreateAccount()
                    } else {
                        hideLoading(this, b.progressbar)
                        showMessage(b.root, "Maaf, email sudah digunakan")
                    }
                } else {
                    hideLoading(this, b.progressbar)
                    task.exception?.message?.let { message -> showMessage(b.root, message) }
                }
            }
    }

    private fun requestCreateAccount() {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                user = auth.currentUser
                requestCreateUserData()
            } else {
                hideLoading(this, b.progressbar)
                task.exception?.message?.let { message -> showMessage(b.root, message) }
            }
        }
    }

    private fun requestCreateUserData() {
        val user = Users(firstName!!, lastName!!, email!!, phone!!)
        firestore.collection(COLLECTION_USER)
            .document(email!!)
            .set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    requestVerifyEmail()
                } else {
                    hideLoading(this, b.progressbar)
                    task.exception?.message?.let { message -> showMessage(b.root, message) }
                }
            }
    }

    private fun requestVerifyEmail() {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful){
                hideLoading(this, b.progressbar)
                showToast(this, getString(R.string.register_succes))
                IntroActivity.Utils.start(this)
            } else {
                hideLoading(this, b.progressbar)
                task.exception?.message?.let { message -> showMessage(b.root, message) }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> finish()
            R.id.btn_register -> register()
        }
    }
}