package com.manshal_khatri.response.fireStore

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.manshal_khatri.response.LeaderBoards
import com.manshal_khatri.response.MainActivity
import com.manshal_khatri.response.PlayerProfileActivity
import com.manshal_khatri.response.RankingFragment
import com.manshal_khatri.response.dataClass.Players
import com.manshal_khatri.response.util.Constants

class FireStore {
        val db = FirebaseFirestore.getInstance()

        fun storeDetails(user : Players, activity: Activity) {
                db.collection(Constants.FS_PLAYER)
                        .document(user.email)
                        .set(user, SetOptions.merge())
                        .addOnSuccessListener {
                                Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                                println("error while storing user details : $it")
//                                d.toggleDialog(dd)
                        }
        }
    fun updateScore(email :String,mode:String,score:Long, activity: Activity) {
        db.collection(Constants.FS_PLAYER)
            .document(email).update(mode,score)
//            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(activity, "Score recorded", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                println("error while storing user details : $it")
//                                d.toggleDialog(dd)
            }
    }
    fun updateName(email :String,name : String,activity: Activity) {
        db.collection(Constants.FS_PLAYER)
            .document(email).update("name",name)
//            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(activity, "Profile Updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                println("error while storing user details : $it")
//                                d.toggleDialog(dd)
            }
    }
    fun updateAvatar(email :String,link : String,activity: Activity) {
        db.collection(Constants.FS_PLAYER)
            .document(email).update("avatar",link)
//            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(activity, "Profile Updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                println("error while storing user details : $it")
//                                d.toggleDialog(dd)
            }
    }
        fun getDetails(activity: Activity){
//                db.collection(Constants.FS_PLAYER).document(user.id)
            Toast.makeText(activity, "fetching", Toast.LENGTH_SHORT).show()
            val sharedPreferences = activity.getSharedPreferences(Constants.SP_GET_PLAYER_DATA,MODE_PRIVATE)
            val curUser= sharedPreferences.getString(Constants.CUR_PLAYER_MAIL,"")
            if (curUser != null) {
                db.collection(Constants.FS_PLAYER).document(curUser)
                    .get().addOnSuccessListener {
//                        it.toObject(Players::class.java)
                        val player = Players(it.get("uid").toString(),it.get("email").toString(),it.get("name").toString(),it.get("avatar").toString(),
                            it.getLong(Constants.MODE_BEGINNERS),
                            it.getLong(Constants.MODE_NORMAL),
                            it.getLong(Constants.MODE_EXPERT),
                            it.getLong(Constants.MODE_RAPID_FIRE))
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
    fun getAllRecords(activity : Fragment,mode : Int) {

        when (activity) {
            is RankingFragment -> {
                db.collection(Constants.FS_PLAYER).get()
                    .addOnSuccessListener {
                    val records = it.documents
                        when(mode){
                            1->activity.setData(records,Constants.MODE_BEGINNERS, activity.context as LeaderBoards)
                            2->activity.setData(records,Constants.MODE_NORMAL, activity.context as LeaderBoards)
                            3->activity.setData(records,Constants.MODE_EXPERT, activity.context as LeaderBoards)
                            4->activity.setData(records,Constants.MODE_RAPID_FIRE, activity.context as LeaderBoards)
                        }

                    }.addOnFailureListener {
                        Toast.makeText(activity.context, "Network Error : $it", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
    fun getCurrentUserId(): String {
        val curUser = FirebaseAuth.getInstance().currentUser
        return curUser?.uid ?: "dummy id"
    }
}
