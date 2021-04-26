package com.android.sipp.ui.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sipp.model.Users
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
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    var userData = MutableLiveData<Users>()
    var loading = MutableLiveData<Boolean>()
    var showMessage = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var successSignIn = MutableLiveData<Boolean>()
    var savePreference = MutableLiveData<Boolean>()

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
                        val user = Users(
                            data?.getString(FIELD_ID)!!,
                            data.getString(FIELD_FIRST_NAME)!!,
                            data.getString(FIELD_LAST_NAME)!!,
                            data.getString(FIELD_EMAIL)!!,
                            data.getString(FIELD_PHONE)!!,
                            data.getString(FIELD_TYPE)!!
                        )
                        requestSignIn(user, email, password)
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
                        requestSignIn(user, email, password)
                    }
                } else {
                    loading.value = false
                    task.exception?.message?.let { message -> errorMessage.value = message }
                    showMessage.value = true
                }
            }
    }

    private fun checkEmailInDriver() {
        //Sementara Check Email Driver belum ada
        errorMessage.value = "Email belum terdaftar, mohon untuk mendaftar terlebih dahulu"
        showMessage.value = true
        loading.value = false
    }

    private fun requestSignIn(userLogin: Users, email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user.isEmailVerified) {
                        userData.value = userLogin
                        savePreference.value = true
                        loading.value = false
                        successSignIn.value = true
                    } else {
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
        savePreference.value = false
        loading.value = false
        showMessage.value = false
        errorMessage.value = ""
        userData.value = null
    }
}