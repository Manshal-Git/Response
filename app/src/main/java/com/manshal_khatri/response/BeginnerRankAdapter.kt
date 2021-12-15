package com.manshal_khatri.response

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.cardview.widget.CardView


import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors.getColor
import com.manshal_khatri.response.dataClass.Players

class BeginnerRankAdapter(val context : Context , val playerList : ArrayList<Players>) : RecyclerView.Adapter<BeginnerRankAdapter.PlayerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_data,parent,false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        playerList.sortByDescending { it.playerScore }
        val players = playerList[position]
        holder.playerName.text=players.playerName
        holder.playerScore.text=players.playerScore.toString()
        holder.rank.text=(position+1).toString()
        holder.avatar.setBackgroundResource(players.avatar)
    }


    override fun getItemCount(): Int {
        return playerList.size
    }
    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val rank : TextView = view.findViewById(R.id.rank)
        val playerName : TextView = view.findViewById(R.id.playerName)
        val playerScore : TextView = view.findViewById(R.id.playerScore)
        val avatar : ImageView=view.findViewById(R.id.profileFrame)
    }

}