package com.manshal_khatri.response.dataClass

import android.widget.ImageView

data class Players(
    val uid: String,
    val email : String,
    val name: String ="",
    val score: Long? = 0,
    val avatar: String = ""
    )