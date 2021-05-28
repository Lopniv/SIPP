package com.android.sipp.ui.activity.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sipp.model.Order
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_PICKUP
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class PaymentViewModel : ViewModel(){

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    var loading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var status = MutableLiveData<Boolean>()

    fun order(email: String, amountPickup: Int, startDate: String, type: String, status: String, statusPayment: Boolean) {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        defaultValue()
        loading.value = true
        val order = Order(email, amountPickup, startDate, type, status, statusPayment)
        firestore.collection(COLLECTION_PICKUP)
            .document(email)
            .set(order)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    this.status.value = true
                    loading.value = false
                } else {
                    this.status.value = false
                    loading.value = false
                    task.exception?.message?.let { message -> errorMessage.value = message }
                }
            }
    }

    private fun defaultValue() {
        status.value = false
        loading.value = false
        errorMessage.value = ""
    }
}