package com.manshal_khatri.response.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.rpc.context.AttributeContext
import com.manshal_khatri.response.databinding.FragLoginBinding
import com.manshal_khatri.response.util.LoadingScreen
import com.manshal_khatri.response.AuthenticationActivity
import com.manshal_khatri.response.FSplayer
import com.manshal_khatri.response.MainActivity
import com.manshal_khatri.response.R
import com.manshal_khatri.response.util.Constants

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFrag() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(activity, "Login Succesful", Toast.LENGTH_SHORT).show()
                d.toggleDialog(dd) //hide
                FSplayer = email
                startActivity(Intent(activity,MainActivity::class.java))
            }else{
                Toast.makeText(activity, it.exception!!.message!!.toString(), Toast.LENGTH_SHORT).show()
                d.toggleDialog(dd) //hide
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}