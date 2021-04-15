package com.android.sipp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = "",
    var type: String = "",
): Parcelable