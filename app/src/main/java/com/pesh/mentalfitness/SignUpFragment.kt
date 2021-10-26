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


class SignUpFragment : Fragment() {
    private lateinit var edtEmail : EditText
    private lateinit var password : EditText
    private lateinit var confirmPassword : EditText
    private lateinit var signUpBtn : Button
    private lateinit var txtLogin : TextView
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
        var view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        edtEmail = view.findViewById(R.id.edSignUpEmail)
        password = view.findViewById(R.id.edSignUpPassword)
        confirmPassword = view.findViewById(R.id.edConfirmPassword)
        signUpBtn = view.findViewById(R.id.btnSignUp)
        txtLogin = view.findViewById(R.id.txtLogin)
        auth = Firebase.auth

        view.findViewById<TextView>(R.id.txtLogin).setOnClickListener {
            var register = activity as FragmentNavigation
            register.navigateFrag(LoginFragment(), false)
        }
        view.findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            validateEmptyForm()

        }

        return view
    }

    private fun firebaseSignUp(){
        txtLogin.isEnabled = false
        txtLogin.alpha = 0.5f
        auth.createUserWithEmailAndPassword(edtEmail.text.toString(), password.text.toString()).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                var navDashboard = activity as FragmentNavigation
                navDashboard.navigateFrag(DashboardFragment(), addToStack = true)

            }else{
                txtLogin.isEnabled = true
                txtLogin.alpha = 1.0f
                Toast.makeText(context,task.exception?.message, Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun validateEmptyForm() {
        when{

            TextUtils.isEmpty(edtEmail.text.toString().trim())->{
                edtEmail.setError("Please enter your email")
            }
            TextUtils.isEmpty(password.text.toString().trim())->{
                password.setError("Please enter your email")
            }
            TextUtils.isEmpty(confirmPassword.text.toString().trim())->{
                confirmPassword.setError("Please enter your email")
            }

            edtEmail.text.toString().trim().isNotEmpty() &&
                    password.text.toString().trim().isNotEmpty() &&
                    confirmPassword.text.toString().trim().isNotEmpty() ->
            {
                if (edtEmail.text.toString().trim().matches(Regex( "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" ))){
                    if (password.text.toString().length >= 5){
                        if (password.text.toString() == confirmPassword.text.toString()){

                            firebaseSignUp()

//                            Toast.makeText(context, "Welcome " + username.text.toString(), Toast.LENGTH_SHORT).show()
                        }else{
                            confirmPassword.setError("Password doesn't match")
                        }

                    }else{
                        password.setError("Password must be more than 5 characters")
                    }

                }else{
                    edtEmail.setError("Please set a valid email")
                }
            }


        }


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SignUpFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}