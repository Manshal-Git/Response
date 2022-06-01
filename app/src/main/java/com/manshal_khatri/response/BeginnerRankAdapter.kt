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
import com.bumptech.glide.Glide
import com.google.android.material.color.MaterialColors.getColor
import com.manshal_khatri.response.dataClass.PlayerInLB
import com.manshal_khatri.response.dataClass.Players
import com.manshal_khatri.response.util.Constants

class BeginnerRankAdapter(val context: Context, val playerList: MutableList<PlayerInLB>) : RecyclerView.Adapter<BeginnerRankAdapter.PlayerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_data,parent,false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        playerList.sortByDescending { it.score }
        val players = playerList[position]
        holder.playerName.text=players.name
        holder.playerScore.text=players.score.toString()
        holder.rank.text=(position+1).toString()
        Glide.with(holder.avatar.context).load(players.avatar).error(Constants.DEF_AVATAR).centerCrop().into(holder.avatar)
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