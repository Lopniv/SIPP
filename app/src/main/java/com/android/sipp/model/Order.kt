package com.android.sipp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    var email: String = "",
    var name: String = "",
    var amountPickup: Int = 0,
    var startDate: String = "",
    var pickupType: String = "",
    var type: String = "",
    var status: String = "",
    var statusPickup: String = "",
    var statusPayment: Boolean = false,
    var address: String = ""
) : Parcelable
