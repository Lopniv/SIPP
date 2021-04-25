package com.android.sipp.ui.activity.introactivity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.sipp.R
import com.android.sipp.adapter.SliderAdapter
import com.android.sipp.databinding.ActivityIntroBinding
import com.android.sipp.ui.activity.CategoryActivity
import com.android.sipp.ui.activity.loginactivity.LoginActivity
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class IntroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var b : ActivityIntroBinding
    private lateinit var adapter: SliderAdapter
    private lateinit var introViewModel: IntroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiate()
        addSliderItem()
        setClickListener()
    }

    private fun initiate() {
        introViewModel = ViewModelProvider(this).get(IntroViewModel::class.java)
        adapter = SliderAdapter(arrayListOf())
    }

    private fun addSliderItem() {
        introViewModel.addItemSlider()
        introViewModel.itemLiveData.observe(this, { items ->
            adapter.updateImage(items)
            setSliderView()
        })
    }

    private fun setSliderView() {
        b.imageSlider.setSliderAdapter(adapter)
        b.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        b.imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        b.imageSlider.indicatorSelectedColor = Color.DKGRAY
        b.imageSlider.indicatorUnselectedColor = Color.GRAY
        b.imageSlider.scrollTimeInSec = 3
        b.imageSlider.isAutoCycle = true
    }

    private fun setClickListener() {
        b.btnLogin.setOnClickListener(this)
        b.btnRegister.setOnClickListener(this)
    }

    private fun login() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
    }

    private fun category() {
        val category = Intent(this, CategoryActivity::class.java)
        startActivity(category)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login -> login()
            R.id.btn_register -> category()
        }
    }
}