package com.manshal_khatri.response.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.manshal_khatri.response.R
import com.manshal_khatri.response.util.Constants
import com.manshal_khatri.response.util.DataStores.preferenceDataStoreAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class AuthenticationActivity : AppCompatActivity() {
//    lateinit var sharedPreferences: SharedPreferences
    var isSignedin = !true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
//        sharedPreferences = getSharedPreferences(Constants.SP_GET_PLAYER_DATA, MODE_PRIVATE)
       CoroutineScope(Dispatchers.IO).launch {
           val t = isLoggedIn(Constants.SP_RW_IS_LOGGED_IN)
           if (t != null) isSignedin = t

           if (!isSignedin) {
           } else {
               playerEmailId = getEmail(Constants.CUR_PLAYER_MAIL)!!
               goToMainScreen()
               finish()
           }
       }
    }

    fun goToMainScreen(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    suspend fun isLoggedIn(key : String) : Boolean? {
        val data = preferenceDataStoreAuth.data.first()
        return data[booleanPreferencesKey(key)]
    }
    suspend fun getEmail(key : String) : String? {
        val data = preferenceDataStoreAuth.data.first()
        return data[stringPreferencesKey(key)]
    }

}