package com.android.sipp.model

data class Order(
    var userId: String = "",
    var amountPickup: Int = 0,
    var startDate: String = "",
    var type: String = "",
    var status: String = "",
    var statusPayment: Boolean = false
)