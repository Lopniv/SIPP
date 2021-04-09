package com.android.sipp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = "",
): Parcelable