package com.manshal_khatri.response

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.navigation.NavigationView
import java.util.*


class LeaderBoards : AppCompatActivity() {

   /* lateinit var highScore1:TextView
    lateinit var topPlayer1:TextView
    lateinit var highScore2:TextView
    lateinit var topPlayer2:TextView
    lateinit var highScore3:TextView
    lateinit var topPlayer3:TextView
    lateinit var highScore4:TextView
    lateinit var topPlayer4:TextView*/
    lateinit var beginnerRanks : Button
    lateinit var expertRanks : Button
    lateinit var godRanks :Button
    lateinit var RFRanks :Button
    lateinit var rankBoards : NavigationView
    lateinit var sharedPreferences: SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_boards)


       /* highScore1=findViewById(R.id.scoreValBeginner)
        topPlayer1=findViewById(R.id.topPlayerBeginner)
        highScore2=findViewById(R.id.scoreValNormal)
        topPlayer2=findViewById(R.id.topPlayerNormal)
        highScore3=findViewById(R.id.scoreValExpert)
        topPlayer3=findViewById(R.id.topPlayerExpert)
        highScore4=findViewById(R.id.scoreValMaster)
        topPlayer4=findViewById(R.id.topPlayerMaster)
        val highScore = arrayListOf<TextView>(highScore1,highScore2,highScore3,highScore4)
        val topPlayer = arrayListOf<TextView>(topPlayer1,topPlayer2,topPlayer3,topPlayer4)*/
       /* beginnerRanks=findViewById(R.id.l0)
        expertRanks=findViewById(R.id.l1)
        godRanks=findViewById(R.id.l2)
        RFRanks=findViewById(R.id.l3)*/
        sharedPreferences=getSharedPreferences(getString(R.string.highScore), MODE_PRIVATE)
        rankBoards=findViewById(R.id.leaderBoardsModes)
        rankBoards.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Mode1 -> defaultFrag()
                R.id.Mode2 -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame,expert_ranks())
//                        .commit()
                }
                R.id.Mode3 -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame,master_ranks())
//                        .commit()
                }
                R.id.Mode4 -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame,RF_ranks())
//                        .commit()
                }
            }
            return@setNavigationItemSelectedListener true
        }

        defaultFrag()



       /* beginnerRanks.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame,beginner_ranks())
                .commit()
        }
        expertRanks.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame,expert_ranks())
                .commit()*/

        for(i in 2 until 6) {
           /* highScore[i-2].text=sharedPreferences.getInt("highScore$i",0).toString()
            topPlayer[i-2].text=sharedPreferences.getString("topPlayer$i","unknown")*/
        }

    }
    fun defaultFrag(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame,beginner_ranks())
            .commit()
    }

}