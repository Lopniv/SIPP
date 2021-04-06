package com.android.sipp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import com.android.sipp.R
import com.android.sipp.databinding.ActivityRegisterBinding
import com.android.sipp.ui.RegisterDetailActivity.Companion.EMAIL
import com.android.sipp.ui.RegisterDetailActivity.Companion.FIRST_NAME
import com.android.sipp.ui.RegisterDetailActivity.Companion.LAST_NAME
import com.android.sipp.ui.RegisterDetailActivity.Companion.PASSWORD
import com.android.sipp.ui.RegisterDetailActivity.Companion.PHONE
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var b : ActivityRegisterBinding

    private var category : Int? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var password: String? = null

    companion object {
        const val CATEGORY = "CATEGORY"
        const val DEFAULT = 0
        const val VALUE_PERSONAL = 1
        const val VALUE_INDUSTRY = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(b.root)
        setClickListener()
        getExtra()
        setView()
        checkData()
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
            //Save Data to firebase
        } else if (category == VALUE_INDUSTRY) {
            val detailRegister = Intent(this, RegisterDetailActivity::class.java)
            detailRegister.putExtra(FIRST_NAME, firstName)
            detailRegister.putExtra(LAST_NAME, lastName)
            detailRegister.putExtra(EMAIL, email)
            detailRegister.putExtra(PHONE, phone)
            detailRegister.putExtra(PASSWORD, password)
            startActivity(detailRegister)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> finish()
            R.id.btn_register -> register()
        }
    }
}