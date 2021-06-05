package com.android.sipp.ui.activity.driver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.sipp.R
import com.android.sipp.databinding.ActivityDetailDriverPickupBinding
import com.android.sipp.model.Order

class DetailDriverPickupActivity : AppCompatActivity() {

    companion object {
        const val ORDER_DATA = "ORDER"
    }

    private lateinit var b: ActivityDetailDriverPickupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailDriverPickupBinding.inflate(layoutInflater)
        setContentView(b.root)
        getData()
    }

    private fun getData() {
        val data = intent.getParcelableExtra<Order>(ORDER_DATA)
        if (data != null) {
            setupData(data)
        }
    }

    private fun setupData(order: Order){
        if (order.statusPayment){
            b.tvPayment.text = "Sudah dibayar"
        } else {
            b.tvPayment.text = "Belum dibayar"
        }
        if (order.statusPickup == "not started"){
            b.tvStatusPickup.text = "Belum dijemput"
        } else if (order.statusPickup == "ongoing") {
            b.tvStatusPickup.text = "Sedang dijemput"
        }
        b.tvName.text = order.name
    }
}