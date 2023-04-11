package com.example.firebase2.activityLab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebase2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*

class signIn : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var loginBtn2: Button
    lateinit var signupBtn2: Button
    lateinit var emailTxt2: EditText
    lateinit var passTxt2: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = Firebase.auth
        loginBtn2 = signinButton2
        signupBtn2 = signupButton2
        passTxt2 = signinPass
        emailTxt2 = signinEmail

        signupBtn2.setOnClickListener {
            startActivity(Intent(this, createAccount::class.java))
        }
        loginBtn2.setOnClickListener {
            if (emailTxt2.text.toString().isEmpty()) {
                emailTxt2.error = "Fill this field"
                emailTxt2.requestFocus()
            } else if (passTxt2.text.toString().isEmpty()) {
                passTxt2.error = "Fill this field"
                passTxt2.requestFocus()
            }
            loginUser(emailTxt2.text.toString(), passTxt2.text.toString())
        }

    }

    private fun loginUser(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Log.d("tag", "signinUser:Success")
                var i = Intent(this, home::class.java)
                startActivity(i)
            } else {
                Log.d("tag", "signinUserWithEmail:failure", task.exception)
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

