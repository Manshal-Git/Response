package com.manshal_khatri.response.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.manshal_khatri.response.R
import com.manshal_khatri.response.fireStore.FireStore
import com.manshal_khatri.response.util.Constants
import com.manshal_khatri.response.util.DataStores.preferenceDataStoreScores
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class GameOver : AppCompatActivity() {

    lateinit var scoreVal : TextView
    lateinit var scoreHead : TextView
    lateinit var topPlayer : EditText
    lateinit var new : TextView
    lateinit var homebtn : Button
    lateinit var playAgainBtn : Button
    lateinit var submit : Button

//    lateinit var sharedPreferences : SharedPreferences
    var scoreIs = 0L
    var highScore=scoreIs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        new=findViewById(R.id.newText)
        scoreHead=findViewById(R.id.scoreHead)
        topPlayer=findViewById(R.id.topPlayer)
        submit=findViewById(R.id.submit)
        scoreVal=findViewById(R.id.scoreVal)
        homebtn=findViewById(R.id.homeBtn)
        playAgainBtn=findViewById(R.id.playAgain)
        val mode=intent.getIntExtra("mode",1)
//        sharedPreferences = getSharedPreferences(getString(R.string.highScore), MODE_PRIVATE)
        scoreIs= intent.getIntExtra("score",0).toLong()
        scoreVal.text=scoreIs.toString()

        lifecycleScope.launch {
            getScore("highScore$mode")?.let {
                highScore = it
            }
//        highScore=sharedPreferences.getInt("highScore$mode",scoreIs)
        if(highScore<scoreIs){
//            sharedPreferences.edit().putInt("highScore$mode",scoreIs).apply()
            storeScore("highScore$mode",scoreIs)
            when(mode){
                1 -> FireStore().updateScore(playerEmailId,Constants.MODE_BEGINNERS, scoreIs,this@GameOver)
                2 -> FireStore().updateScore(playerEmailId,Constants.MODE_NORMAL, scoreIs,this@GameOver)
                3 -> FireStore().updateScore(playerEmailId,Constants.MODE_EXPERT, scoreIs,this@GameOver)
                4 -> FireStore().updateScore(playerEmailId,Constants.MODE_RAPID_FIRE, scoreIs,this@GameOver)
            }
            new.text="New"
            scoreHead.text="HighScore"
        }
        }
        homebtn.setOnClickListener {
            finish()                            // should be handled efficiently try to know how ?
        }
        playAgainBtn.setOnClickListener {
            val intent : Intent
            if(mode == 4  ) {
                 intent = Intent(this@GameOver, RapidFirePS::class.java)
            }else {
                 intent = Intent(this@GameOver, PlayScreen::class.java)
                intent.putExtra("mode", mode)
            }
            startActivity(intent)
            finish()                            // should be handled efficiently try to know how ?
        }

    }
    suspend fun getScore(key : String): Long? {
        val data = preferenceDataStoreScores.data.first()
        return data[longPreferencesKey(key)]
    }

    suspend fun storeScore(k: String, score:Long){
        preferenceDataStoreScores.edit {
            it[longPreferencesKey(k)] = score
        }
    }

}