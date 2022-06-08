package com.manshal_khatri.response

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manshal_khatri.response.activities.AuthenticationActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {
    lateinit var intro : MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        intro = MediaPlayer.create(this@SplashScreen, R.raw.quik)
        intro.start()

        Timer("settingUp", false).schedule(1000) {

            val intent = Intent(this@SplashScreen , AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}