package com.android.sipp.ui.activity.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sipp.utils.Utils
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_PICKUP
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_ID
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {
    private lateinit var firestore: FirebaseFirestore

    var loading = MutableLiveData<Boolean>()
    var showMessage = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var successRegister = MutableLiveData<Boolean>()

    fun checkStatusPickup(userId: String) {
        firestore = FirebaseFirestore.getInstance()
        defaultValue()
        loading.value = true
        firestore.collection(COLLECTION_PICKUP)
            .document(userId)
            .get()
            .addOnSuccessListener{ document ->
                if (document != null){
                    Log.e("TAG", "DOC: ${document.data}")
                } else {
//                    loading.value = false
//                    document.exception?.message?.let { message -> errorMessage.value = message }
//                    showMessage.value = true
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