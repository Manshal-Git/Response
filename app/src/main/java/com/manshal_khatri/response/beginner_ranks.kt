package com.manshal_khatri.response

import android.app.Activity
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
import com.manshal_khatri.response.fireStore.FireStore


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [beginner_ranks.newInstance] factory method to
 * create an instance of this fragment.
 */
class beginner_ranks : Fragment() {

    lateinit var topPlayerList : RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    /*val playersList = arrayListOf<Players>(
        Players("manshal",10,R.drawable.ic_check) ,
                Players("man",20,R.drawable.ic_lives),
    Players("manshal",30,R.drawable.background_dark),Players("manshal",10,R.drawable.ic_check) ,
        Players("man",20,R.drawable.ic_lives),
        Players("manshal",30,R.drawable.background_dark),Players("manshal",10,R.drawable.ic_check) ,
        Players("man",20,R.drawable.ic_lives),
        Players("manshal",30,R.drawable.background_dark),Players("manshal",10,R.drawable.ic_check) ,
        Players("man",20,R.drawable.ic_lives),
        Players("manshal",30,R.drawable.background_dark)
    Players("m",40),
    Players("sul",5),
    Players("sahal",2)*/


   // lateinit var dataAdapter: BeginnerRankAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_beginner_ranks, container, false)
        topPlayerList = view.findViewById(R.id.TopPlayersList)
        layoutManager = LinearLayoutManager(activity)
        FireStore().getAllRecords(activity as Context)
        if(playersList.isNotEmpty())

        topPlayerList.adapter=BeginnerRankAdapter(activity as Context,playersList)
        topPlayerList.layoutManager=layoutManager
        return view
    }
    fun setData(list : MutableList<DocumentSnapshot>){
        for (element in list){
            println(element)
        }
    }
}