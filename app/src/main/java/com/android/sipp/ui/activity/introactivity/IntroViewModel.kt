package com.android.sipp.ui.activity.introactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sipp.R
import com.android.sipp.model.SlideModel

class IntroViewModel : ViewModel() {

    private var itemSlider: ArrayList<SlideModel> = arrayListOf()
    var itemLiveData = MutableLiveData<ArrayList<SlideModel>>()

    fun addItemSlider(){
        itemSlider.add(SlideModel(R.drawable.intro_1, "Geanka", "Aplikasi yang membantu untuk mengatasi masalah sampahmu"))
        itemSlider.add(SlideModel(R.drawable.intro_2, "Geanka", "Mari peduli pada lingkungan demi kebersihan dan kenyamanan"))
        itemSlider.add(SlideModel(R.drawable.intro_3, "Geanka", "Aplikasi mudah dan cepat untuk membantu kamu"))
        itemLiveData.value = itemSlider
    }
}