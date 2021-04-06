package com.android.sipp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.sipp.R
import com.android.sipp.databinding.ActivityCategoryBinding
import com.android.sipp.ui.RegisterActivity.Companion.CATEGORY
import com.android.sipp.ui.RegisterActivity.Companion.VALUE_INDUSTRY
import com.android.sipp.ui.RegisterActivity.Companion.VALUE_PERSONAL

class CategoryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var b : ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(b.root)
        setClickListener()
    }

    private fun setClickListener() {
        b.btnBack.setOnClickListener(this)
        b.cvCategoryIn.setOnClickListener(this)
        b.cvCategoryPer.setOnClickListener(this)
    }

    private fun industry() {
        val industry = Intent(this, RegisterActivity::class.java)
        industry.putExtra(CATEGORY, VALUE_INDUSTRY)
        startActivity(industry)
    }

    private fun personal() {
        val personal = Intent(this, RegisterActivity::class.java)
        personal.putExtra(CATEGORY, VALUE_PERSONAL)
        startActivity(personal)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> finish()
            R.id.cv_category_in -> industry()
            R.id.cv_category_per -> personal()
        }
    }
}