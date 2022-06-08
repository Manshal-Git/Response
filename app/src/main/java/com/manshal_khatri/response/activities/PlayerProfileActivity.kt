package com.manshal_khatri.response.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem

import android.view.View.*
import android.widget.*

import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.manshal_khatri.response.R
import com.manshal_khatri.response.dataClass.Players
import com.manshal_khatri.response.databinding.ActivityPlayerProfileBinding
import com.manshal_khatri.response.fireStore.FireStore
import com.manshal_khatri.response.util.Constants
import com.manshal_khatri.response.util.DataStores.preferenceDataStoreAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class PlayerProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerProfileBinding
    // Layouts
    lateinit var relativeLayout : RelativeLayout
//    lateinit var button: AppCompatButton
//    lateinit var signOut : AppCompatButton
    lateinit var profileImg : ImageView
    lateinit var edittxt :EditText
    lateinit var TVname : TextView
    lateinit var signOut : TextView

    lateinit var saveNameBtn : AppCompatButton
    private val fireStore = FireStore()

//    lateinit var loginState : View
    lateinit var sharedPreferences : SharedPreferences  //TODO : NEED TO BE REPLACED WITH DATASTORE FOR SAVING PROPIC
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_profile)
//        button = findViewById(R.id.BtnChooseImage)

        // ACTIONBAR
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Profile"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        saveNameBtn = findViewById(R.id.BtnChangeName)

        relativeLayout = findViewById(R.id.relativeLayer)
        signOut = findViewById(R.id.signOut)
        edittxt = findViewById(R.id.ETName)
        TVname = findViewById(R.id.TVPlayerName)
        profileImg = findViewById(R.id.IVprofilePicture)
        binding = ActivityPlayerProfileBinding.inflate(layoutInflater)  // Binding.Inflate(layoutInflater) used in Activities
        //loginState= findViewById(R.id.loginState)
        sharedPreferences = getSharedPreferences(Constants.SP_GET_PLAYER_DATA, MODE_PRIVATE)
        val mypic = sharedPreferences.getString(Constants.PROFILE_IMAGE,Constants.DEF_AVATAR)
            ?.toUri()
        Glide.with(this).load(mypic).circleCrop().into(profileImg)
        fireStore.getDetails(this)


        signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            CoroutineScope(Dispatchers.IO).launch{
                    preferenceDataStoreAuth.edit {
                        it[booleanPreferencesKey(Constants.SP_RW_IS_LOGGED_IN)] = false
                    }
            }
            val intent = Intent(this@PlayerProfileActivity, AuthenticationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editProfile -> {
                openEditMode()
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun openEditMode(){
        relativeLayout.background = resources.getDrawable(R.drawable.editing_bg)
        saveNameBtn.visibility = VISIBLE
        edittxt.visibility = VISIBLE
        edittxt.setText(TVname.text.toString())
        TVname.visibility = INVISIBLE
        profileImg.foreground = resources.getDrawable(R.drawable.ic_edit)
        profileImg.setOnClickListener {
            chooseImage()
        }
        saveNameBtn.setOnClickListener {
            saveChanges()
        }
    }
    fun saveChanges(){
        with(edittxt){
            setSelection(this.length())
            setText("$text ")
            TVname.text = text.trim()
            visibility = GONE
        }
        saveNameBtn.visibility = GONE
        TVname.visibility = VISIBLE
        relativeLayout.background = null
    }
    private fun chooseImage(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            val storageIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(storageIntent,Constants.CHOOSE_IMAGE)
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),Constants.READ_STORAGE_PERMISSION)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode==Activity.RESULT_OK){
            if(requestCode == Constants.CHOOSE_IMAGE){
                if(data!=null){
                    try{
                        val selectedImageUri = data.data!!
                        Glide.with(this).load(selectedImageUri).circleCrop().into(profileImg)
                        val propicStorageRef : StorageReference = FirebaseStorage.getInstance().reference.child("Image"+System.currentTimeMillis())
                        propicStorageRef.putFile(selectedImageUri)
                            .addOnSuccessListener {
                                it.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                                    fireStore.updateAvatar(playerEmailId, it.toString(),this)
                                }
                                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show() }
                            .addOnFailureListener{
                                Toast.makeText(this, "something went wrong ", Toast.LENGTH_SHORT).show()
                            println("On FAILURE : $it")}
                        sharedPreferences.edit().putString("profilePic",selectedImageUri.toString()).apply()
                    }catch(e : Exception){
                        e.printStackTrace()
                    }
                }
            }
        }else{
            Toast.makeText(this, "Req canceled", Toast.LENGTH_SHORT).show()
        }
        profileImg.setOnClickListener {  }
        profileImg.foreground = null
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun setDetails(player : Players){
            TVname.text = player.name
//            TVscore.text = player.rapidFireScore.toString()
    }

    override fun onResume() {
        fireStore.getDetails(this)
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FSplayerName = TVname.text.toString()
        FireStore().updateName(playerEmailId, FSplayerName, this)
    }
}