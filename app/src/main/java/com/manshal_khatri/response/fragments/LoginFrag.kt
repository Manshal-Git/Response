package com.manshal_khatri.response.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.manshal_khatri.response.MainActivity
import com.manshal_khatri.response.R
import com.manshal_khatri.response.databinding.FragLoginBinding
import com.manshal_khatri.response.playerEmailId
import com.manshal_khatri.response.util.Constants
import com.manshal_khatri.response.util.DataStores
import com.manshal_khatri.response.util.DataStores.preferenceDataStoreAuth
import com.manshal_khatri.response.util.DataStores.preferenceDataStoreScores
import com.manshal_khatri.response.util.LoadingScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LoginFrag() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_login, container, false)
        val binding = FragLoginBinding.bind(view)
        val email = binding.ETEmail
        val password = binding.ETPassword
        with(binding){
            TVRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFrag_to_registerFrag) }
            TVForgotPassword.setOnClickListener { findNavController().navigate(R.id.action_loginFrag_to_forgotPasswordFragment) }
            BtnLogin.setOnClickListener {
                Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                 if(!isDataFillled(email))else if(!isDataFillled(password))else{
                    registeruser(email.text.toString(),password.text.toString())
                }
            }
        }
        return view
    }
    fun isDataFillled(view: TextView) : Boolean{
        if (TextUtils.isEmpty(view.text.toString().trim() { it <= ' ' })) {
            Snackbar.make(view, view.hint.toString() + " is required", 1000).show()
            return false
        }
        return true
    }
    fun registeruser(email : String,password : String){
        val d = LoadingScreen(activity as Context)
        val dd = d.loadingScreen()
        d.toggleDialog(dd) //show
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener{ it ->
            if(it.isSuccessful){
                Toast.makeText(activity, "Login Succesful", Toast.LENGTH_SHORT).show()
                d.toggleDialog(dd) //hide
                playerEmailId = email
                CoroutineScope(Dispatchers.IO).launch{
                   activity?.let{  ds ->
                       ds.preferenceDataStoreAuth.edit {
                           it[booleanPreferencesKey(Constants.SP_RW_IS_LOGGED_IN)] = true
                           it[stringPreferencesKey(Constants.CUR_PLAYER_MAIL)] = email
                       }
                   }
                }
                startActivity(Intent(activity,MainActivity::class.java))
            }else{
                Toast.makeText(activity, it.exception!!.message!!.toString(), Toast.LENGTH_SHORT).show()
                d.toggleDialog(dd) //hide
            }
        }
    }

}