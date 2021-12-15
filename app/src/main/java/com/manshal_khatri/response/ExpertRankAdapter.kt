package com.manshal_khatri.response

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.response.dataClass.Players


class ExpertRankAdapter(val context : Context, val playerList : ArrayList<Players>) : RecyclerView.Adapter<ExpertRankAdapter.PlayerViewHolder>(){



    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val rank : TextView = view.findViewById(R.id.rank)
        val playerName : TextView = view.findViewById(R.id.playerName)
        val playerScore : TextView = view.findViewById(R.id.playerScore)
        val avatar : ImageView =view.findViewById(R.id.profileFrame)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_data,parent,false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val players = playerList[position]
        holder.avatar.setBackgroundResource(players.avatar)
        holder.playerName.text=players.playerName
        holder.playerScore.text=players.playerScore.toString()
        holder.rank.text=(position+1).toString()
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

}