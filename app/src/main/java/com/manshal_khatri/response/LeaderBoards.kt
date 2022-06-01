package com.manshal_khatri.response

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.navigation.NavigationView
import com.manshal_khatri.response.dataClass.PlayerInLB
import com.manshal_khatri.response.databinding.ActivityLeaderBoardsBinding
import com.manshal_khatri.response.fireStore.FireStore
import com.manshal_khatri.response.util.Constants


val playersList = mutableListOf<PlayerInLB>()
class LeaderBoards : AppCompatActivity() {

    lateinit var rankBoards : NavigationView
    lateinit var binding: ActivityLeaderBoardsBinding
    lateinit var process : ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_boards)
        binding = ActivityLeaderBoardsBinding.inflate(layoutInflater)
        process = findViewById(R.id.processingScreen)
        rankBoards=findViewById(R.id.leaderBoardsModes)
        loadFragment(RankingFragment(),1)

        rankBoards.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Mode1 -> {
                    Constants.showProgress(process)
                    loadFragment(RankingFragment(),1)

                }
                R.id.Mode2 -> {
                    Constants.showProgress(process)
                    loadFragment(RankingFragment(),2)

                }
                R.id.Mode3 -> {
                    Constants.showProgress(process)
                    loadFragment(RankingFragment(),3)
                }
                R.id.Mode4 -> {
                    Constants.showProgress(process)
                    loadFragment(RankingFragment(),4)
                }

            }
            return@setNavigationItemSelectedListener true
        }

    }
    private fun loadFragment(frag : Fragment,mode : Int){
        FireStore().getAllRecords(frag,mode)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame,frag)
            .commit()
    }
}