package com.android.sipp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemPickup(
    val title: String,
    val image: Int,
    val price: Int,
    val desc: String
) : Parcelable