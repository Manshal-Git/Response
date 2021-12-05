package com.manshal_khatri.response

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    /*private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding*/
    lateinit var beginner : Button
    lateinit var normal : Button
    lateinit var expert : Button
    lateinit var masters : Button
    lateinit var highScores : FloatingActionButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beginner=findViewById(R.id.beginner)
        normal=findViewById(R.id.normal)
        expert=findViewById(R.id.expert)
        masters=findViewById(R.id.master)
        highScores=findViewById(R.id.fab)





        beginner.setOnClickListener {
            val intent = Intent(this@MainActivity , PlayScreen::class.java)
            intent.putExtra("mode",1)
            startActivity(intent)
            onPause()
        }
        normal.setOnClickListener {
            val intent = Intent(this@MainActivity , PlayScreen::class.java)
            intent.putExtra("mode",2)
            startActivity(intent)
            onPause()
        }
        expert.setOnClickListener {
            val intent = Intent(this@MainActivity , PlayScreen::class.java)
            intent.putExtra("mode",3)
            startActivity(intent)
            onPause()
        }
        masters.setOnClickListener {
            Toast.makeText(this@MainActivity, "Coming soon ;)", Toast.LENGTH_SHORT).show()
            /*val intent = Intent(this@MainActivity , PlayScreen::class.java)
            intent.putExtra("mode",4)
            startActivity(intent)*/
            onPause()
        }
        highScores.setOnClickListener {
            val intent = Intent(this@MainActivity , LeaderBoards::class.java)
            startActivity(intent)
            onPause()
        }
    }
}