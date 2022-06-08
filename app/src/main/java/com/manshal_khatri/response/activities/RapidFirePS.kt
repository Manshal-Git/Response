package com.manshal_khatri.response.activities

//import android.support.v7.app.AppCompatActivity
import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.manshal_khatri.response.R
import java.util.*
import kotlin.concurrent.schedule

class RapidFirePS : AppCompatActivity() {
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
    lateinit var streakMsg : TextView
    lateinit var streakBonus : TextView


     // logic building variables
    var mode = 4
    var difficulty = 1
    var timerVal = 40
//    var defaultLife = 5
    var seen = false
    var doReset = false
    var chosen = 0
    var que =0
    var ans ="0"
    var options = 0
    var streak = 0
    lateinit var musicCorrect : MediaPlayer
    lateinit var musicWrong : MediaPlayer
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rapid_fire_ps)

        // link to componants
        musicCorrect = MediaPlayer.create(this@RapidFirePS, R.raw.correct)
        musicWrong = MediaPlayer.create(this@RapidFirePS, R.raw.wrong)

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

        scoreVal=findViewById(R.id.scoreVal)
        streakBonus=findViewById(R.id.streakBonus)
        streakMsg=findViewById(R.id.streakMsg)
        timeProgress=findViewById(R.id.timeBar)
        val buttons = arrayListOf(b0,b1,b2,b3,b4,b5,b6,b7,b8)

        var score = 0
        val timeup=0
        val modeTime = timerVal * 1000

        // Logic functions
        fun gameOver(){
            val intent = Intent(this@RapidFirePS, GameOver::class.java)
            intent.putExtra("score",score)
            intent.putExtra("mode",mode)
            startActivity(intent)
            finish()
        }
         fun rePuzzle(){
            chosen = if(seen){
                (0..8).random()
            }else{
                (0..5).random()
            }
            que=(10..99).random()
            ans=que.toString()
            question.text=ans

        }

        fun scoreUpdater(){
            if(score>9) {
                scoreVal.text = score.toString()
            }else{
                "0$score".also { scoreVal.text = it }
            }
        }
       /* fun setStreak(){
            streak=intent.getIntExtra("streakVal",streak)
        }*/
        fun resetStreak(){
            streak=0
        }
        fun streakHider(){
            streakBonus.visibility=TextView.INVISIBLE
            streakMsg.visibility=TextView.INVISIBLE
        }

        fun onCorrect(){
            println("streak $streak")
            //setStreak()
            musicCorrect.start()
//            doReset=true
            streak++
            if(streak%5!=0){
                score++
            }else{
                val bonus=streak/5+1
                streakBonus.text="+"+bonus.toString()
                streakMsg.text="$streak in row"
                streakMsg.visibility=TextView.VISIBLE
                streakBonus.visibility=TextView.VISIBLE
                score+=bonus

            }
        }
        fun levelUp(){
            when(score){
                7 -> difficulty++
                14 -> {  difficulty++
                        seen=true
                }
                21 -> difficulty++
            }
        }

        fun formatButtons(){
            for(i in 0 until 9){
                buttons[i].setBackgroundColor(getColor(R.color.white))
                buttons[i].setTextColor(getColor(R.color.black))
                if(i!=chosen){ //  WRONG BUTTONS SETTER
                    if(difficulty%2==0){
                        val queL=(que%10)*10
                        options=((queL)..(queL+9)).random()
                    }else {
                        options = (10..99).random()
                    }
                    if (options != que) {
                        buttons[i].text = options.toString()
                    } else {
                        buttons[i].text = "9"
                    }
                    // when wrong answer clicked
                    buttons[i].setOnClickListener {
                        buttons[i].setBackgroundColor(getColor(R.color.love))
                        resetStreak()
                        musicWrong.start()
//                        onIncorrect()
                    }
                }else{ // CORRECT BUTTON SETTER
                    buttons[chosen].text=ans
                    // when correct answer clicked
                    buttons[chosen].setOnClickListener {
                        buttons[chosen].setBackgroundColor(getColor(R.color.safe))
//                        buttons[i].foreground=getDrawable(R.drawable.ic_check)
//                        buttons[i].text=""
//                        buttons[i].foregroundGravity=-1
                        onCorrect()
                        scoreUpdater()
                        levelUp()
                        rePuzzle()
                        formatButtons()

                    }
                }
                if(seen && i>5){
                    buttons[i].visibility=Button.VISIBLE
                }
            }
        }
        musicCorrect.setOnCompletionListener {
            musicCorrect.pause()
        }
        musicWrong.setOnCompletionListener {
            musicWrong.pause()
        }
        rePuzzle()
        formatButtons()

        /*fun makePuzzel(){

            chosen = if(seen){
                (0..8).random()
            }else{
                (0..5).random()
            }
            que=(10..99).random()
            ans=que.toString()
            question.text=ans

        }*/
        /*fun playing(){
            scoreUpdater()
            makePuzzel()
            formatButtons()
        }*/


            Timer("settingUp", false).schedule(modeTime.toLong()) {
//                onTimeup()
                /*if(!doReset){
                    gameOver()
                }*/
                gameOver()
//                finish()
            }
          // Timebar animator
        ObjectAnimator.ofInt(timeProgress,"progress",timeup)
            .setDuration(modeTime.toLong())
            .start()
    }
    override fun onBackPressed(){

    }
}