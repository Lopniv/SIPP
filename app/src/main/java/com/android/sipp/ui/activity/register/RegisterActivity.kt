package com.android.sipp.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.sipp.R
import com.android.sipp.databinding.ActivityRegisterBinding
import com.android.sipp.model.Users
import com.android.sipp.ui.activity.intro.IntroActivity
import com.android.sipp.utils.Utils
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_INDUSTRY
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_USER
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_EMAIL
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_FIRST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_ID
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_LAST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_PHONE
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_TYPE
import com.android.sipp.utils.Utils.Keys.CATEGORY
import com.android.sipp.utils.Utils.Keys.PASSWORD
import com.android.sipp.utils.Utils.Keys.VALUE_INDUSTRY
import com.android.sipp.utils.Utils.Keys.VALUE_PERSONAL
import com.android.sipp.utils.Utils.hideLoading
import com.android.sipp.utils.Utils.showLoading
import com.android.sipp.utils.Utils.showMessage
import com.android.sipp.utils.Utils.showToast
import com.android.sipp.utils.Utils.start
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlin.random.Random

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private var category : String? = null
    private var id: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var password: String? = null

    private lateinit var b : ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var user: FirebaseUser
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiate()
        setClickListener()
        getExtra()
        setView()
        checkData()
    }

    private fun initiate() {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    private fun setClickListener() {
        b.btnRegister.setOnClickListener(this)
        b.btnBack.setOnClickListener(this)
    }

    private fun getExtra() {
        category = intent.getStringExtra(CATEGORY)
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

    private fun isValidated() : Boolean {
        return if (password?.length!! >= 8){
            true
        } else {
            showMessage(b.root, "Password kamu kurang dari 8 karakter")
            false
        }
    }

    private fun register() {
        id = Random.nextInt(100000, 200000).toString()
        Log.e("TAG", "$id")
        if (category == VALUE_PERSONAL){
            checkEmailInUser()
        } else if (category == VALUE_INDUSTRY) {
            val detailRegister = Intent(this, RegisterDetailActivity::class.java)
            detailRegister.putExtra(FIELD_ID, id)
            detailRegister.putExtra(FIELD_FIRST_NAME, firstName)
            detailRegister.putExtra(FIELD_LAST_NAME, lastName)
            detailRegister.putExtra(FIELD_EMAIL, email)
            detailRegister.putExtra(FIELD_PHONE, phone)
            detailRegister.putExtra(FIELD_TYPE, category)
            detailRegister.putExtra(PASSWORD, password)
            startActivity(detailRegister)
        }
    }

    private fun checkEmailInUser() {
        registerViewModel.checkEmailInUser(email!!, password!!, id!!, firstName!!, lastName!!, phone!!, category!!)
        registerViewModel.loading.observe(this, { isLoading ->
            if(isLoading == true) {
                showLoading(this, b.progressbar)
            } else {
                hideLoading(this, b.progressbar)
            }
        })
        registerViewModel.successRegister.observe(this, { isSuccess ->
            if (isSuccess == true) {
                showToast(this, getString(R.string.register_succes))
                start(this, IntroActivity::class.java)
            }
        })
        registerViewModel.errorMessage.observe(this, { errorMessage ->
            registerViewModel.showMessage.observe(this, { showMessage ->
                if (showMessage == true) {
                    showMessage(b.root, errorMessage)
                }
            })
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> finish()
            R.id.btn_register -> if(isValidated()) register()
        }
    }
}