package com.pesh.mentalfitness

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var edtLoginEmail : EditText
    private lateinit var edtLoginPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        edtLoginPassword = view.findViewById(R.id.edtLoginPassword)
        edtLoginEmail = view.findViewById(R.id.edtLoginEmail)
        btnLogin = view.findViewById(R.id.btnLogin)
        auth = Firebase.auth

        view.findViewById<TextView>(R.id.txtSignUp).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(SignUpFragment(), false)
        }
        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            validateForm()

        }

        return view
    }

    private fun firebaseSignIn() {
        btnLogin.isEnabled = false
        btnLogin.alpha = 0.5f
        auth.signInWithEmailAndPassword(edtLoginEmail.text.toString(),
                edtLoginPassword.text.toString()).addOnCompleteListener {
                    task ->
            if (task.isSuccessful){
                var navDashboard = activity as FragmentNavigation
                navDashboard.navigateFrag(DashboardFragment(), addToStack = true)

            }else{
                btnLogin.isEnabled = true
                btnLogin.alpha = 1.0f
                Toast.makeText(context,task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm() {
        when{
            TextUtils.isEmpty(edtLoginEmail.text.toString().trim())->{
                edtLoginEmail.setError("Please enter your email")
            }
            TextUtils.isEmpty(edtLoginPassword.text.toString().trim())->{
                edtLoginPassword.setError("Please enter your email")
            }


            edtLoginEmail.text.toString().trim().isNotEmpty() &&
                    edtLoginPassword.text.toString().trim().isNotEmpty() ->
            {
                if (edtLoginEmail.text.toString().trim().matches(Regex( "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" ))){
                   firebaseSignIn()

                }else{
                    edtLoginEmail.setError("Please set a valid email")
                }
            }


        }


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}