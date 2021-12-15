package com.manshal_khatri.response.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.response.ExpertRankAdapter
import com.manshal_khatri.response.R
import com.manshal_khatri.response.dataClass.Players

class expert_ranks : Fragment() {
  lateinit var topPlayerList : RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
  lateinit var dataAdapter: ExpertRankAdapter
    val playersList = arrayListOf<Players>(
        Players("khatri",10,R.drawable.ic_check),
        Players("man",20,R.drawable.ic_lives),
        Players("kohli",30,R.drawable.background_dark), Players("manshal",10,R.drawable.ic_check) ,
        Players("netaji",20,R.drawable.ic_lives),
        Players("gandhi",30,R.drawable.background_dark), Players("manshal",10,R.drawable.ic_check) ,
        Players("patel",20,R.drawable.ic_lives),
        Players("yadav",30,R.drawable.background_dark), Players("manshal",10,R.drawable.ic_check) ,
        Players("lalaji",20,R.drawable.ic_lives),
        Players("manshal",30,R.drawable.background_dark)
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_beginner_ranks, container, false)
        topPlayerList = view.findViewById(R.id.TopPlayersList)
        layoutManager = LinearLayoutManager(activity)
        dataAdapter= ExpertRankAdapter(activity as Context,playersList)
        topPlayerList.adapter=dataAdapter
        topPlayerList.layoutManager=layoutManager
        return view
    }

}