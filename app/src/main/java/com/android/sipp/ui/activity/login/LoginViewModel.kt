package com.android.sipp.ui.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sipp.model.Driver
import com.android.sipp.model.Industry
import com.android.sipp.model.Personal
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_DRIVER
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_INDUSTRY
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_USER
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_EMAIL
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_FIRST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_ID
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_LAST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_PHONE
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_TYPE
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var user: FirebaseUser

    var personalData = MutableLiveData<Personal>()
    var industryData = MutableLiveData<Industry>()
    var driverData = MutableLiveData<Driver>()
    var loading = MutableLiveData<Boolean>()
    var showMessage = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var successSignIn = MutableLiveData<Boolean>()
    var type = MutableLiveData<String>()

    fun checkEmailInUser(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        defaultValue()
        loading.value = true
        firestore.collection(COLLECTION_USER)
            .whereEqualTo(FIELD_EMAIL, email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.result?.isEmpty == true){
                        checkEmailInIndustry(email, password)
                    } else {
                        val data = task.result?.documents?.get(0)
                        val user = Personal(
                            data?.getString(FIELD_ID)!!,
                            data.getString(FIELD_FIRST_NAME)!!,
                            data.getString(FIELD_LAST_NAME)!!,
                            data.getString(FIELD_EMAIL)!!,
                            data.getString(FIELD_PHONE)!!,
                            data.getString(FIELD_TYPE)!!
                        )
                        requestSignIn(user, email, password, "personal")
                    }
                } else {
                    loading.value = false
                    task.exception?.message?.let { message -> errorMessage.value = message }
                    showMessage.value = true
                }
            }
    }

    private fun checkEmailInIndustry(email: String, password: String) {
        firestore.collection(COLLECTION_INDUSTRY)
            .whereEqualTo(FIELD_EMAIL, email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.result?.isEmpty == true){
                        checkEmailInDriver(email, password)
                    } else {
                        val data = task.result?.documents?.get(0)
                        val industry = Industry(
                            data?.getString(FIELD_ID)!!,
                            data.getString(FIELD_FIRST_NAME)!!,
                            data.getString(FIELD_LAST_NAME)!!,
                            data.getString(FIELD_EMAIL)!!,
                            data.getString(FIELD_PHONE)!!,
                            data.getString(FIELD_TYPE)!!
                        )
                        requestSignIn(industry, email, password, "industry")
                    }
                } else {
                    loading.value = false
                    task.exception?.message?.let { message -> errorMessage.value = message }
                    showMessage.value = true
                }
            }
    }

    private fun checkEmailInDriver(email: String, password: String) {
        firestore.collection(COLLECTION_DRIVER)
            .whereEqualTo(FIELD_EMAIL, email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.result?.isEmpty == true){
                        loading.value = false
                        errorMessage.value = "Email belum terdaftar, mohon untuk mendaftar terlebih dahulu"
                        showMessage.value = true
                    } else {
                        val data = task.result?.documents?.get(0)
                        val driver = Driver(
                            data?.getString(FIELD_ID)!!,
                            data.getString(FIELD_FIRST_NAME)!!,
                            data.getString(FIELD_LAST_NAME)!!,
                            data.getString(FIELD_EMAIL)!!,
                            data.getString(FIELD_PHONE)!!,
                            data.getString(FIELD_TYPE)!!
                        )
                        requestSignIn(driver, email, password, "driver")
                    }
                } else {
                    loading.value = false
                    task.exception?.message?.let { message -> errorMessage.value = message }
                    showMessage.value = true
                }
            }
    }

    private fun requestSignIn(userLogin: Any, email: String, password: String, type: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    user = auth.currentUser!!
                    if (user.isEmailVerified) {
                        when(type){
                            "personal" -> {
                                personalData.value = userLogin as Personal
                                this.type.value = "personal"
                            }
                            "industry" -> {
                                industryData.value = userLogin as Industry
                                this.type.value = "industry"
                            }
                            "driver" -> {
                                driverData.value = userLogin as Driver
                                this.type.value = "driver"
                            }
                        }
                        loading.value = false
                        successSignIn.value = true
                    } else {
                        if (type == "driver") user.sendEmailVerification()
                        auth.signOut()
                        loading.value = false
                        errorMessage.value = "Email kamu belum terverifikasi"
                        showMessage.value = true
                    }
                } else {
                    loading.value = false
                    task.exception?.message?.let { message -> errorMessage.value = message }
                    showMessage.value = true
                }
            }
    }

    private fun defaultValue() {
        successSignIn.value = false
        loading.value = false
        showMessage.value = false
        errorMessage.value = ""
        type.value = ""
        personalData.value = Personal()
        industryData.value = Industry()
        driverData.value = Driver()
    }
}