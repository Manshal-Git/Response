package com.manshal_khatri.response.fragments


import android.content.Context
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
import com.manshal_khatri.response.AuthenticationActivity
import com.manshal_khatri.response.FSplayer
import com.manshal_khatri.response.R
import com.manshal_khatri.response.databinding.FragRegisterBinding
import com.manshal_khatri.response.dataClass.Players
import com.manshal_khatri.response.fireStore.FireStore
import com.manshal_khatri.response.util.LoadingScreen

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.frag_register, container, false)
        val binding = FragRegisterBinding.bind(view)
        val name = binding.ETName
        val email = binding.ETEmail
        val password = binding.ETPassword
        binding.TVLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFrag_to_loginFrag)
        }
        binding.BtnRegister.setOnClickListener {
            if(!isDataFillled(name))else if(!isDataFillled(email))else if(!isDataFillled(password))else{
                registeruser(name.text.toString(),email.text.toString(),password.text.toString())
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
    fun registeruser(name : String,email : String,password : String){
        val d = LoadingScreen(activity as Context)
        val dd = d.loadingScreen()
        d.toggleDialog(dd)  // show
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful){
                FSplayer = email
                FireStore().storeDetails(
                    Players("999", FSplayer,name)
                , activity as AuthenticationActivity
                )
                    /*d.toggleDialog(dd) //hide
                    Toast.makeText(activity, "Registration Succesful", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()*/
                    d.toggleDialog(dd) //hide
                    findNavController().navigate(R.id.action_registerFrag_to_loginFrag)
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
         * @return A new instance of fragment RegisterFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
