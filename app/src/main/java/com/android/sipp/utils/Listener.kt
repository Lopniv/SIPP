package com.android.sipp.utils

import com.android.sipp.model.Order

interface Listener {
    fun onItemClick(order: Order)
}