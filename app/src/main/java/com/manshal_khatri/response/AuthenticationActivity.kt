package com.manshal_khatri.response

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.manshal_khatri.response.util.Constants


class AuthenticationActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    var isSignedin = !true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        sharedPreferences = getSharedPreferences(Constants.SP_GET_PLAYERDATA, MODE_PRIVATE)
        isSignedin = sharedPreferences.getBoolean(Constants.SP_RW_IS_LOGGED_IN,false)

        if(!isSignedin) {
            Toast.makeText(this, "welcome", Toast.LENGTH_SHORT).show()
        }else{
           goToMainScreen()
            FSplayer = sharedPreferences.getString(Constants.CUR_PLAYER,"").toString()
            finish()
        }
    }

    fun goToMainScreen(){
        startActivity(Intent(this,MainActivity::class.java))
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}