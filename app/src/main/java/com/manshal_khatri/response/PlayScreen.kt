package com.manshal_khatri.response

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.*
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import org.w3c.dom.Text
import java.util.*
import kotlin.concurrent.schedule


class PlayScreen : AppCompatActivity() {

    /* variables and components */
    //core gameplay UI
    lateinit var question : TextView
    lateinit var b1 : Button
    lateinit var b2 : Button
    lateinit var b3 : Button
    lateinit var b4 : Button
    lateinit var b5 : Button
    lateinit var b0 : Button
    lateinit var b6 : Button
    lateinit var b7 : Button
    lateinit var b8 : Button

   // gameplay status
    lateinit var timeProgress : ProgressBar
    lateinit var lifes : TextView
    lateinit var scoreVal : TextView

    // logic building variables
    var mode = 0
    var timerVal = 5
    var defaultLife = 5
    var seen = false
    var doReset = false
    var chosen = 0
    var que =0
    var ans ="0"
    var options = 0
    lateinit var musicCorrect : MediaPlayer
    lateinit var musicWrong : MediaPlayer







    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_screen)

        // link to componants
        musicCorrect = MediaPlayer.create(this@PlayScreen, R.raw.correct)
        musicWrong = MediaPlayer.create(this@PlayScreen, R.raw.wrong)

        b0=findViewById(R.id.button1)
        b1=findViewById(R.id.button2)
        b2=findViewById(R.id.button3)
        b3=findViewById(R.id.button4)
        b4=findViewById(R.id.button5)
        b5=findViewById(R.id.button6)
        b6=findViewById(R.id.button7)
        b7=findViewById(R.id.button8)
        b8=findViewById(R.id.button9)
        question=findViewById(R.id.question)
        lifes=findViewById(R.id.lifenum)
        scoreVal=findViewById(R.id.scoreVal)
        timeProgress=findViewById(R.id.timeBar)
        val buttons = arrayListOf<Button>(b0,b1,b2,b3,b4,b5,b6,b7,b8)
      // game mode parameteres
        fun Mode(lives:Int,times:Int,Seen:Boolean) {
            defaultLife = lives
            timerVal = times
            seen = Seen
        }
        mode=intent.getIntExtra("mode",mode)
        when(mode){
            1-> Mode(5,5,false)
            2 ->Mode(3,4,false)
            3 ->Mode(3,4,true)
            4 ->Mode(3,3,true)
            }

        var life = defaultLife
        var score = 0
        val timeup=0
        val modeTime = timerVal * 1000
      // Logic functions
        fun gameOver(){
          val intent = Intent(this@PlayScreen,GameOver::class.java)
          intent.putExtra("score",score)
          intent.putExtra("mode",mode)
          musicWrong.release()
          musicCorrect.release()
          startActivity(intent)
          finish()
        }
        fun updateLifes(){
            life=intent.getIntExtra("life",life)
            lifes.text=life.toString()
        }
        fun scoreUpdater(){
            score=intent.getIntExtra("score",score)
            if(score>9) {
                scoreVal.text = score.toString()
            }else{
                scoreVal.text = "0"+ score.toString()
            }
        }
        fun onCorrect(){
            val intent = Intent(this@PlayScreen,PlayScreen::class.java)
            intent.putExtra("life",life)
            musicCorrect.start()
            doReset=true
            score++
            intent.putExtra("score",score)
            intent.putExtra("mode",mode)
            startActivity(intent)
            finish()
        }
        fun onIncorrect() {
            musicWrong.start()
            life--
            lifes.text=life.toString()
            if(life==0) {
                doReset = true
                gameOver()
                finish()
            }
        }
        // Buttons functionality formatter
        fun formatButtons(){
            for(i in 0 until 9){
                buttons[i].setBackgroundColor(getColor(R.color.white))
                buttons[i].setTextColor(getColor(R.color.black))
                if(i!=chosen){
                    options=(10..99).random()
                    if(options!=que){
                        buttons[i].text=options.toString()
                    }else{
                        buttons[i].text="9"
                    }
                    // when wrong answer clicked
                    buttons[i].setOnClickListener {
                        buttons[i].setBackgroundColor(getColor(R.color.love))
                        onIncorrect()
                    }
                }else{
                    buttons[chosen].text=ans
                    // when correct answer clicked
                    buttons[chosen].setOnClickListener {
//                        buttons[chosen].setBackgroundColor(getColor(R.color.white))
                        buttons[i].foreground=getDrawable(R.drawable.ic_check)
                        buttons[i].text=""
                        buttons[i].foregroundGravity=-1
                        onCorrect()
                    }
                }
                if(seen && i>5){
                    buttons[i].visibility=Button.VISIBLE
                }
            }
        }
        fun makePuzzel(){

            chosen = if(seen){
                (0..8).random()
            }else{
                (0..5).random()
            }
            que=(10..99).random()
            ans=que.toString()
            question.text=ans

            formatButtons()
            updateLifes()
            scoreUpdater()
            Timer("settingUp", false).schedule(modeTime.toLong()) {
                if(!doReset){
                    gameOver()
                }
                finish()
            }
        }

        //  puzzle(quetion) maker

        makePuzzel()

       // Timebar animator
        ObjectAnimator.ofInt(timeProgress,"progress",timeup)
            .setDuration(modeTime.toLong())
            .start()
    }
    override fun onBackPressed(){

    }

}