package com.android.sipp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.sipp.R
import com.android.sipp.adapter.ItemPickupAdapter
import com.android.sipp.databinding.ActivityPickupCategoryBinding
import com.android.sipp.model.ItemPickup

class PickupCategoryActivity : AppCompatActivity(), View.OnClickListener {

    private var model: ArrayList<ItemPickup> = arrayListOf()

    private lateinit var adapter: ItemPickupAdapter
    private lateinit var b: ActivityPickupCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPickupCategoryBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiate()
        setListener()
        setupItemPickup()
    }

    private fun initiate() {
        adapter = ItemPickupAdapter(arrayListOf(), this)
    }

    private fun setListener() {
        b.btnBack.setOnClickListener(this)
    }

    private fun setupItemPickup() {
        model.add(
            ItemPickup(
                "Penjemputan Terjadwal",
                R.drawable.bg_pickup,
                    30000,
                "Penjemputan terjadwal dilakukan 3x/minggu, total penjemputan 12x/bulan"
            )
        )
        model.add(
            ItemPickup(
                "Penjemputan Langsung",
                R.drawable.bg_pickup,
                    50000,
                "Penjemputan akan dilakukan 1 hari setelah permintaan penjemputan"
            )
        )
        adapter.updateItem(model)

        b.viewPager.adapter = adapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> finish()
        }
    }
}