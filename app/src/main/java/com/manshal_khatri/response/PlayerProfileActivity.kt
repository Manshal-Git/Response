package com.manshal_khatri.response

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater

import android.view.View
import android.view.View.*
import android.widget.EditText

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.manshal_khatri.response.dataClass.Players
import com.manshal_khatri.response.databinding.ActivityPlayerProfileBinding
import com.manshal_khatri.response.fireStore.FireStore
import com.manshal_khatri.response.util.Constants
import java.lang.Exception


class PlayerProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerProfileBinding
    lateinit var button: AppCompatButton
    lateinit var signOut : AppCompatButton
    lateinit var img : ImageView
    lateinit var edittxt :EditText
    lateinit var TVname : TextView
    lateinit var  TVscore : TextView
    lateinit var editNameBtn : AppCompatButton

//    lateinit var loginState : View
    lateinit var sharedPreferences : SharedPreferences
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_profile)
        button = findViewById(R.id.BtnChooseImage)
        editNameBtn = findViewById(R.id.BtnChangeName)
        signOut = findViewById(R.id.btnSignout)
        edittxt = findViewById(R.id.ETName)
        TVname = findViewById(R.id.TVPlayerName)
        img = findViewById(R.id.IVprofilePicture)
        TVscore = findViewById(R.id.TVHighScoreVal)
        binding = ActivityPlayerProfileBinding.inflate(layoutInflater)  // Binding.Inflate(layoutInflater) used in Activities
        //loginState= findViewById(R.id.loginState)
        sharedPreferences = getSharedPreferences(Constants.SP_GET_PLAYERDATA, MODE_PRIVATE)
        val mypic = sharedPreferences.getString("profilePic","https://www.freeiconspng.com/thumbs/profile-icon-png/profile-icon-9.png")
            ?.toUri()
        Glide.with(this).load(mypic).into(img)
        FireStore().getDetails(this)
        button.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

                val storageIntent = Intent(Settings.ACTION_SETTINGS,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(storageIntent,Constants.CHOOSE_IMAGE)
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),Constants.READ_STORAGE_PERMISSION)
            }
        }
        signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            sharedPreferences.edit().putBoolean(Constants.SP_RW_IS_LOGGED_IN,false).apply()
            startActivity(Intent(this@PlayerProfileActivity,AuthenticationActivity::class.java))
            finish()
        }
       editNameBtn.setOnClickListener {
           editToggler()
       }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun editToggler(){
        if(!edittxt.isVisible){
            editNameBtn.foreground = getDrawable(R.drawable.ic_check)
            edittxt.visibility = VISIBLE
            TVname.visibility = INVISIBLE
//            Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()  // DEPRECATED
        }else{
            TVname.text = edittxt.text
            editNameBtn.foreground = getDrawable(R.drawable.ic_edit)
            edittxt.visibility = INVISIBLE
            TVname.visibility = VISIBLE
//            Toast.makeText(this, "no", Toast.LENGTH_SHORT).show()  //DEPRECATED
        }

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode==Activity.RESULT_OK){
            if(requestCode == Constants.CHOOSE_IMAGE){
                if(data!=null){
                    try{
                        val selectedImageUri = data.data!!
                        Glide.with(this).load(selectedImageUri).into(img)
                        sharedPreferences.edit().putString("profilePic",selectedImageUri.toString()).apply()
                    }catch(e : Exception){
                        e.printStackTrace()
                    }
                }
            }
        }else{
            Toast.makeText(this, "Req canceled", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun setDetails(player : Players){
            TVname.text = player.name
            TVscore.text = player.score.toString()
    }

    override fun onResume() {
        FireStore().getDetails(this)
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FSplayerName = TVname.text.toString()
        val uid = sharedPreferences.getString(Constants.CUR_PLAYER,"")
        uid?.let { Players("101",it, FSplayerName, TVscore.text.toString().toLong() ) }?.let { FireStore().storeDetails(it,this) }
    }
}