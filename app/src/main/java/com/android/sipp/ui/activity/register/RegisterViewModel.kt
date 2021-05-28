package com.android.sipp.ui.activity.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sipp.model.Personal
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_INDUSTRY
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_USER
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_EMAIL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var user: FirebaseUser

    var loading = MutableLiveData<Boolean>()
    var showMessage = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var successRegister = MutableLiveData<Boolean>()

    fun checkEmailInUser(email: String, password: String, id: String, firstName: String, lastName: String, phone: String, category: String) {
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
                        checkEmailInIndustry(email, password, id, firstName, lastName, phone, category)
                    } else {
                        loading.value = false
                        errorMessage.value = "Maaf, email sudah digunakan"
                        showMessage.value = true
                    }
                } else {
                    loading.value = false
                    task.exception?.message?.let { message -> errorMessage.value = message }
                    showMessage.value = true
                }
            }
    }

    private fun checkEmailInIndustry(email: String, password: String, id: String, firstName: String, lastName: String, phone: String, category: String) {
        firestore.collection(COLLECTION_INDUSTRY)
            .whereEqualTo(FIELD_EMAIL, email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.result?.isEmpty == true){
                        requestCreateAccount(email, password, id, firstName, lastName, phone, category)
                    } else {
                        loading.value = false
                        errorMessage.value = "Maaf, email sudah digunakan"
                        showMessage.value = true
                    }
                } else {
                    loading.value = false
                    task.exception?.message?.let { message -> errorMessage.value = message }
                    showMessage.value = true
                }
            }
    }

    private fun requestCreateAccount(email: String, password: String, id: String, firstName: String, lastName: String, phone: String, category: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                user = auth.currentUser!!
                requestCreateUserData(email, id, firstName, lastName, phone, category)
            } else {
                loading.value = false
                task.exception?.message?.let { message -> errorMessage.value = message }
                showMessage.value = true
            }
        }
    }

    private fun requestCreateUserData(email: String, id: String, firstName: String, lastName: String, phone: String, category: String) {
        val user = Personal(id, firstName, lastName, email, phone, category)
        firestore.collection(COLLECTION_USER)
            .document(email)
            .set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    requestVerifyEmail()
                } else {
                    loading.value = false
                    task.exception?.message?.let { message -> errorMessage.value = message }
                    showMessage.value = true
                }
            }
    }

    private fun requestVerifyEmail() {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful){
                loading.value = false
                successRegister.value = true
            } else {
                loading.value = false
                task.exception?.message?.let { message -> errorMessage.value = message }
                showMessage.value = true
            }
        }
    }

    private fun defaultValue() {
        successRegister.value = false
        loading.value = false
        showMessage.value = false
        errorMessage.value = ""
    }
}