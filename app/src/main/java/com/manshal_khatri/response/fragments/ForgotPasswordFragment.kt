package com.manshal_khatri.response.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.manshal_khatri.response.R
import com.manshal_khatri.response.databinding.FragmentForgotPasswordBinding
import com.manshal_khatri.response.util.LoadingScreen

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgotPasswordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
lateinit var emailET : EditText
lateinit var binding: FragmentForgotPasswordBinding
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
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        binding = FragmentForgotPasswordBinding.bind(view)
         emailET = view.findViewById(R.id.editText)
        binding.btnReset.setOnClickListener {
            if(!TextUtils.isEmpty(emailET.text.toString().trim(){it <= ' ' })){
                val d = LoadingScreen(activity as Context)
                val dd = d.createLoadingDialog()
                d.toggleDialog(dd)
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailET.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(activity, "Check your email box", Toast.LENGTH_SHORT).show()
                        d.toggleDialog(dd)
                    }else{
                        Toast.makeText(activity, "${it.exception!!.message!!}.", Toast.LENGTH_SHORT).show()
                        d.toggleDialog(dd)
                    }
                }
            }else{
                Toast.makeText(activity, "please enter email address", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForgotPasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForgotPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}