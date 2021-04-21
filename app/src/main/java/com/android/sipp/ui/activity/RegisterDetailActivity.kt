package com.android.sipp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.sipp.R
import com.android.sipp.databinding.ActivityRegisterDetailBinding
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

class RegisterDetailActivity : AppCompatActivity() {

    private lateinit var b : ActivityRegisterDetailBinding

    private var id: Int? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var type: String? = null
    private var password: String? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var user: FirebaseUser
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterDetailBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiateFirebase()
        getExtra()
    }

    private fun initiateFirebase() {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
//        val storage = FirebaseStorage.getInstance()
//        storageReference = storage.reference.child(REFERENCE_IMAGE_PROFILE_ADMINS)
    }

    private fun getExtra() {
        id = intent.getIntExtra(FIELD_ID, 0)
        firstName = intent.getStringExtra(FIELD_FIRST_NAME)
        lastName = intent.getStringExtra(FIELD_LAST_NAME)
        email = intent.getStringExtra(FIELD_EMAIL)
        phone = intent.getStringExtra(FIELD_PHONE)
        type = intent.getStringExtra(FIELD_TYPE)
        password = intent.getStringExtra(PASSWORD)
    }
}