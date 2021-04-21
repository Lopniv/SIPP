package com.android.sipp.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.sipp.R
import com.android.sipp.adapter.SliderAdapter
import com.android.sipp.databinding.ActivityIntroBinding
import com.android.sipp.model.SlideModel
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class IntroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var b : ActivityIntroBinding
    private lateinit var adapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(b.root)
        instanceAdapter()
        addSliderItem()
        setClickListener()
    }

    private fun instanceAdapter() {
        adapter = SliderAdapter(arrayListOf())
    }

    private fun addSliderItem() {
        val items = ArrayList<SlideModel>()
        items.add(SlideModel(R.drawable.intro_1, "Geanka", "Aplikasi yang membantu untuk mengatasi masalah sampahmu"))
        items.add(SlideModel(R.drawable.intro_2, "Geanka", "Mari peduli pada lingkungan demi kebersihan dan kenyamanan"))
        items.add(SlideModel(R.drawable.intro_3, "Geanka", "Aplikasi mudah dan cepat untuk membantu kamu"))
        adapter.updateImage(items)
        setSliderView()
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