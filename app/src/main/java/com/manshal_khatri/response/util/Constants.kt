package com.manshal_khatri.response.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.manshal_khatri.response.R

object Constants {
    const val DEF_AVATAR = "https://www.freeiconspng.com/thumbs/profile-icon-png/profile-icon-9.png"
    const val READ_STORAGE_PERMISSION = 1
    const val CHOOSE_IMAGE = 2
    const val SP_RW_IS_LOGGED_IN = "isLoggedIn"
    const val SP_GET_PLAYER_DATA = "PlayerData"
    const val CUR_PLAYER_MAIL = "email"
    const val FS_PLAYER = "responser"
    const val MODE_BEGINNERS = "beginnerScore"
    const val MODE_NORMAL = "normalScore"
    const val MODE_EXPERT = "expertScore"
    const val MODE_RAPID_FIRE = "rapidFireScore"
    const val PROFILE_IMAGE = "profilePic"

    fun showProgress(view: View){
        view.visibility=VISIBLE
    }
    fun hideProgress(view:View){
        view.visibility=GONE
    }
}