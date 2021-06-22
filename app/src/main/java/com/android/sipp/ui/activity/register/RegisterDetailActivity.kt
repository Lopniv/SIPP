package com.android.sipp.ui.activity.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.android.sipp.R
import com.android.sipp.databinding.ActivityRegisterDetailBinding
import com.android.sipp.ui.activity.intro.IntroActivity
import com.android.sipp.utils.Utils
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_ADDRESS
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_EMAIL
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_FIRST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_ID
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_LAST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_PHONE
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_TYPE
import com.android.sipp.utils.Utils.Keys.PASSWORD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class RegisterDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var b : ActivityRegisterDetailBinding

    private var id: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var category: String? = null
    private var address: String? = null
    private var password: String? = null
    private var pickup: String? = null
    private var sumTrash: String? = null
    private var description: String? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var user: FirebaseUser
    private lateinit var storageReference: StorageReference
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterDetailBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiateFirebase()
        getExtra()
        setClickListener()
        checkData()
    }

    private fun initiateFirebase() {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
//        val storage = FirebaseStorage.getInstance()
//        storageReference = storage.reference.child(REFERENCE_IMAGE_PROFILE_ADMINS)
    }

    private fun getExtra() {
        id = intent.getStringExtra(FIELD_ID)
        firstName = intent.getStringExtra(FIELD_FIRST_NAME)
        lastName = intent.getStringExtra(FIELD_LAST_NAME)
        email = intent.getStringExtra(FIELD_EMAIL)
        phone = intent.getStringExtra(FIELD_PHONE)
        category = intent.getStringExtra(FIELD_TYPE)
        address = intent.getStringExtra(FIELD_ADDRESS)
        password = intent.getStringExtra(PASSWORD)
    }

    private fun setClickListener() {
        b.btnBack.setOnClickListener(this)
        b.btnRegister.setOnClickListener(this)
    }

    private fun checkData() {
        b.btnRegister.isEnabled = false
        b.etPickup.addTextChangedListener(textWatcher)
        b.etSumTrash.addTextChangedListener(textWatcher)
        b.etTrashDescription.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            b.btnRegister.isEnabled =
                b.etPickup.text.toString().isNotEmpty() &&
                        b.etSumTrash.text.toString().isNotEmpty() &&
                        b.etTrashDescription.text.toString().isNotEmpty()
            pickup = b.etPickup.text.toString()
            sumTrash = b.etSumTrash.text.toString()
            description = b.etTrashDescription.text.toString()
        }
    }

    private fun register() {
        registerViewModel.checkEmailInUser(email!!, password!!, id.toString(), firstName!!, lastName!!, phone!!, category!!, address!!, pickup!!, sumTrash!!, description!!)
        registerViewModel.loading.observe(this, { isLoading ->
            if(isLoading == true) {
                Utils.showLoading(this, b.progressbar)
            } else {
                Utils.hideLoading(this, b.progressbar)
            }
        })
        registerViewModel.successRegister.observe(this, { isSuccess ->
            if (isSuccess == true) {
                Utils.showToast(this, getString(R.string.register_succes))
                Utils.start(this, IntroActivity::class.java)
            }
        })
        registerViewModel.errorMessage.observe(this, { errorMessage ->
            registerViewModel.showMessage.observe(this, { showMessage ->
                if (showMessage == true) {
                    Utils.showMessage(b.root, errorMessage)
                }
            })
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> finish()
            R.id.btn_register -> register()
        }
    }
}