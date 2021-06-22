package com.android.sipp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Personal(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = "",
    var type: String = "",
    var address: String = ""
): Parcelable