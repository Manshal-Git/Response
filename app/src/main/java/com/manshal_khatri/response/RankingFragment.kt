package com.manshal_khatri.response

import android.content.Context
import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.manshal_khatri.response.dataClass.PlayerInLB
import com.manshal_khatri.response.util.Constants

class RankingFragment : Fragment() {

    lateinit var topPlayerRV : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_beginner_ranks, container, false)
        topPlayerRV = view.findViewById(R.id.TopPlayersList)
        topPlayerRV.layoutManager= LinearLayoutManager(activity)
        return view
    }
    fun setData(list : MutableList<DocumentSnapshot>,mode : String , parent : LeaderBoards){
        playersList.clear()
        for (element in list){
//            println(element)
            val name = element.data?.get("name").toString()
            val score = element.data?.get(mode).toString().toInt()
            val avatar = element.data?.get("avatar").toString()
            println("NAME || SCORE : $score")
            playersList.add(PlayerInLB(name , score,avatar))
        }
        topPlayerRV.adapter=BeginnerRankAdapter(activity as Context,playersList)
        Constants.hideProgress(parent.process)
    }
}