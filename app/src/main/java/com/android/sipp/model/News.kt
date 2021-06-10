package com.android.sipp.model

data class News(
    var source: String = "",
    var time: String = "",
    var title: String = "",
    var content: String = "",
    var link: String = "",
    var image: Int = 0
)
