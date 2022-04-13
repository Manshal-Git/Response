package com.manshal_khatri.response

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.manshal_khatri.response.databinding.ActivityPlayerProfileBinding
import com.manshal_khatri.response.util.Constants
import java.lang.Exception

class PlayerProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerProfileBinding
    lateinit var button: Button
    lateinit var img : ImageView
    lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_profile)
        button = findViewById(R.id.button)
        img = findViewById(R.id.imageView2)
        sharedPreferences = getSharedPreferences("PlayerData", MODE_PRIVATE)
        val mypic = sharedPreferences.getString("profilePic","https://www.freeiconspng.com/thumbs/profile-icon-png/profile-icon-9.png")
            ?.toUri()
        Glide.with(this).load(mypic).into(img)
        button.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

                val storageIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(storageIntent,Constants.CHOOSE_IMAGE)


            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),Constants.READ_STORAGE_PERMISSION)
            }
        }
    }

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
}