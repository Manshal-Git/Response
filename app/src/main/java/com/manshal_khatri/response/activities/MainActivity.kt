package com.manshal_khatri.response.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.isVisible
import androidx.core.view.marginStart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manshal_khatri.response.R
import com.manshal_khatri.response.dataClass.Players
import com.manshal_khatri.response.databinding.ActivityMainBinding
import com.manshal_khatri.response.fireStore.FireStore
import com.manshal_khatri.response.util.Constants
import kotlinx.coroutines.*


//var player : FirebaseUser? = null
var playerEmailId : String = ""
var FSplayerName : String = ""

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var beginner : Button
    lateinit var normal : Button
    lateinit var expert : Button
    lateinit var masters : Button
    lateinit var playerProfile : AppCompatButton
    lateinit var highScores : FloatingActionButton
//    lateinit var sharedPreferences: SharedPreferences
//    var isSignedin = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        sharedPreferences = getSharedPreferences(Constants.SP_GET_PLAYER_DATA, MODE_PRIVATE)
        beginner = findViewById(R.id.beginner)
        normal = findViewById(R.id.normal)
        expert = findViewById(R.id.expert)
        masters = findViewById(R.id.master)
        highScores = findViewById(R.id.fab)
        val modeSelectTextView = findViewById<TextView>(R.id.modeSelect)
        playerProfile = findViewById(R.id.playerProfileBtn)

        CoroutineScope(Dispatchers.IO).launch {
            while(true){
                    val job = CoroutineScope(Dispatchers.Main).launch {
                        if(modeSelectTextView.isVisible) modeSelectTextView.visibility = GONE
                        else modeSelectTextView.visibility = VISIBLE
                    }
                delay(555)
                job.cancel(null)
            }
        }
        beginner.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayScreen::class.java)
            intent.putExtra("mode", 1)
            startActivity(intent)
            onPause()
        }
        normal.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayScreen::class.java)
            intent.putExtra("mode", 2)
            startActivity(intent)
            onPause()
        }
        expert.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayScreen::class.java)
            intent.putExtra("mode", 3)
            startActivity(intent)
            onPause()
        }
        masters.setOnClickListener {
//            Toast.makeText(this@MainActivity, "Coming soon ;)", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, RapidFirePS::class.java)
            intent.putExtra("mode", 4)
            startActivity(intent)
            onPause()
        }
        highScores.setOnClickListener {
            val intent = Intent(this@MainActivity, LeaderBoards::class.java)
            startActivity(intent)
            onPause()
        }
        playerProfile.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayerProfileActivity::class.java)
            startActivity(intent)
            onPause()
        }
        /*isSignedin = sharedPreferences.getBoolean(Constants.SP_RW_IS_LOGGED_IN, false)
        sharedPreferences.edit().putBoolean(Constants.SP_RW_IS_LOGGED_IN, true).apply()
        sharedPreferences.edit().putString(Constants.CUR_PLAYER_MAIL, playerEmailId).apply()*/


        // SHOULD BE DEPRECATED IN THIS APP TO MANAGE ACCOUNTS MORE EFFICIENTLY
        /*if(!isSignedin) {
            val signInLauncher = registerForActivityResult(
                FirebaseAuthUIActivityResultContract()
            ) { res ->
                this.onSignInResult(res,sharedPreferences)
                Toast.makeText(this, "almost there", Toast.LENGTH_LONG).show()
            }

            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                //AuthUI.IdpConfig.GoogleBuilder().build(),
            )

            // Create and launch sign-in intent
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)
        }else{
            FSplayer = sharedPreferences.getString(Constants.CUR_PLAYER,"").toString()
        }
        }*/
    }
    fun centerView(view : View){
        val center = resources.configuration.screenWidthDp/2
    }
    fun fetchUser(players: Players){
        FireStore().storeDetails(players,this)
    }

    // Previously Implementation of Sign out
   /* override fun onResume() {
        super.onResume()
        val logedin =  sharedPreferences.getBoolean(Constants.SP_RW_IS_LOGGED_IN,false)
        if(!logedin){
            finish()
        }
    }*/
}