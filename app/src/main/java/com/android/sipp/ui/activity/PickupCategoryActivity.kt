package com.android.sipp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.sipp.R
import com.android.sipp.databinding.ActivityPickupCategoryBinding

class PickupCategoryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var b: ActivityPickupCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPickupCategoryBinding.inflate(layoutInflater)
        setContentView(b.root)
        setListener()
    }

    private fun setListener() {
        b.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> finish()
        }
    }
}