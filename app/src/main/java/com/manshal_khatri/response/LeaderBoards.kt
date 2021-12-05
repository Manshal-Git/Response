package com.manshal_khatri.response

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*
import kotlin.concurrent.schedule


class LeaderBoards : AppCompatActivity() {

    lateinit var highScore1:TextView
    lateinit var topPlayer1:TextView
    lateinit var highScore2:TextView
    lateinit var topPlayer2:TextView
    lateinit var highScore3:TextView
    lateinit var topPlayer3:TextView
    lateinit var highScore4:TextView
    lateinit var topPlayer4:TextView
    lateinit var sharedPreferences: SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_boards)

        highScore1=findViewById(R.id.scoreValBeginner)
        topPlayer1=findViewById(R.id.topPlayerBeginner)
        highScore2=findViewById(R.id.scoreValNormal)
        topPlayer2=findViewById(R.id.topPlayerNormal)
        highScore3=findViewById(R.id.scoreValExpert)
        topPlayer3=findViewById(R.id.topPlayerExpert)
        highScore4=findViewById(R.id.scoreValMaster)
        topPlayer4=findViewById(R.id.topPlayerMaster)
        val highScore = arrayListOf<TextView>(highScore1,highScore2,highScore3,highScore4)
        val topPlayer = arrayListOf<TextView>(topPlayer1,topPlayer2,topPlayer3,topPlayer4)
        sharedPreferences=getSharedPreferences(getString(R.string.highScore), MODE_PRIVATE)
        for(i in 1 until 5) {
            highScore[i-1].text=sharedPreferences.getInt("highScore$i",0).toString()
            topPlayer[i-1].text=sharedPreferences.getString("topPlayer$i","unknown")
        }

    }
}