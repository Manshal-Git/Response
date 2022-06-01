package com.manshal_khatri.response

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.manshal_khatri.response.util.Constants
import com.manshal_khatri.response.util.DataStores
import com.manshal_khatri.response.util.DataStores.preferenceDataStoreAuth
import com.manshal_khatri.response.util.DataStores.preferenceDataStoreScores
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class AuthenticationActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    var isSignedin = !true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        sharedPreferences = getSharedPreferences(Constants.SP_GET_PLAYER_DATA, MODE_PRIVATE)
       CoroutineScope(Dispatchers.IO).launch {
           val t = isLoggedIn(Constants.SP_RW_IS_LOGGED_IN)
           if (t != null) isSignedin = t

           if (!isSignedin) {
//               Toast.makeText(this@AuthenticationActivity, "welcome", Toast.LENGTH_SHORT).show()
           } else {
               playerEmailId = getEmail(Constants.CUR_PLAYER_MAIL)!!
               goToMainScreen()
               finish()
           }
       }
    }

    fun goToMainScreen(){
        startActivity(Intent(this,MainActivity::class.java))
    }

    override fun onPause() {
        super.onPause()
        finish()
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