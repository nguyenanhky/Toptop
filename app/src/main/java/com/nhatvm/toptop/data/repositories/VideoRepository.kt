package com.nhatvm.toptop.data.repositories

import com.nhatvm.toptop.R
import javax.inject.Inject

class VideoRepository @Inject constructor(

) {
    private val videos = listOf(
        R.raw.khesanh,
        R.raw.thanhcoquangtri,
        R.raw.vuahung
    )

    fun getVideo() = videos.random()
}