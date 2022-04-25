package com.manshal_khatri.response.fireStore

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.manshal_khatri.response.LeaderBoards
import com.manshal_khatri.response.MainActivity
import com.manshal_khatri.response.PlayerProfileActivity
import com.manshal_khatri.response.beginner_ranks
import com.manshal_khatri.response.dataClass.Players
import com.manshal_khatri.response.util.Constants

class FireStore {
        val db = FirebaseFirestore.getInstance()

        fun storeDetails(user : Players, activity: Activity) {
                db.collection(Constants.FS_PLAYER)
                        .document(user.email).set(user, SetOptions.merge())  // setOptions = merge means
                        // we can add unfilled details later on to the profile like profile image
                        .addOnSuccessListener {
                                Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                                Log.e(null,"error while storing user details",it)
//                                d.toggleDialog(dd)
                        }
        }
        fun getDetails(activity: Activity){
//                db.collection(Constants.FS_PLAYER).document(user.id)
            Toast.makeText(activity, "fetching", Toast.LENGTH_SHORT).show()
            val sharedPreferences = activity.getSharedPreferences(Constants.SP_GET_PLAYERDATA,MODE_PRIVATE)
            val curUser= sharedPreferences.getString(Constants.CUR_PLAYER,"")
            if (curUser != null) {
                db.collection(Constants.FS_PLAYER).document(curUser)
                    .get().addOnSuccessListener {
                        val player = Players(it.get("uid").toString(),it.get("email").toString(),it.get("name").toString(),it.getLong("score"),it.get("avatar").toString())
                        println(player)
                        when(activity) {
                            is PlayerProfileActivity -> {
                                activity.setDetails(player)
                            }
                            is MainActivity -> {
                                activity.fetchUser(player)
                            }
                        }
                    }.addOnFailureListener {
                        when(activity){
                            is PlayerProfileActivity -> {
                                Toast.makeText(activity, "$it OCCURED", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }
    fun getAllRecords(activity : Context){
        db.collection(Constants.FS_PLAYER).get().addOnSuccessListener {
            val records = it.documents
            when(activity){
                is LeaderBoards -> {
                    activity.setData(records)
                }

            }
        }
    }
}