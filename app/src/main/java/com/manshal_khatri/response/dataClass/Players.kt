package com.manshal_khatri.response.dataClass

import android.widget.ImageView

data class Players(
    val uid: String,
    val email : String,
    val name: String ="",
    val avatar: String = "",
    val beginnerScore: Long? = 0,
    val normalScore: Long? = 0,
    val expertScore: Long? = 0,
    val rapidFireScore: Long? = 0,
    )