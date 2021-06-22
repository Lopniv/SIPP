package com.android.sipp.ui.activity.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sipp.model.Order
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_PICKUP
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {
    private lateinit var firestore: FirebaseFirestore

    var loading = MutableLiveData<Boolean>()
    var status = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var order = MutableLiveData<Order>()

    fun checkStatusPickup(email: String) {
        firestore = FirebaseFirestore.getInstance()
        defaultValue()
        loading.value = true
        firestore.collection(COLLECTION_PICKUP)
            .document(email)
            .get()
            .addOnSuccessListener{ document ->
                if (document.exists()){
                    val amountPickup = document.data?.get("amountPickup") as Long
                    val startDate = document.data?.get("startDate") as String
                    val status = document.data?.get("status") as String
                    val statusPayment = document.data?.get("statusPayment") as Boolean
                    val pickupType = document.data?.get("pickupType") as String
                    val type = document.data?.get("type") as String
                    val email = document.data?.get("email") as String
                    val name = document.data?.get("name") as String
                    val statusPickup = document.data?.get("statusPickup") as String
                    val address = document.data?.get("address") as String
                    val order = Order(
                        email,
                        name,
                        amountPickup.toInt(),
                        startDate,
                        pickupType,
                        type,
                        status,
                        statusPickup,
                        statusPayment,
                        address
                    )
                    Log.e("TAG", "DATA: $order")

                    this.order.value = order
                    loading.value = false
                    this.status.value = true
                } else {
                    loading.value = false
                    this.status.value = false
                    errorMessage.value = "Document not available"
                }
            }
            .addOnFailureListener{
                loading.value = false
                this.status.value = false
                errorMessage.value = it.localizedMessage
            }
    }

    private fun defaultValue() {
        order.value = Order()
        loading.value = false
        status.value = false
        errorMessage.value = ""
    }
}