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
import kotlinx.android.synthetic.main.activity_create_account.*

class createAccount : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var loginBtn: Button
    lateinit var signupBtn: Button
    lateinit var emailTxt: EditText
    lateinit var passTxt: EditText

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            var i = Intent(this, home::class.java)
            startActivity(i)        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        auth = Firebase.auth
        loginBtn = signinButton
        signupBtn = signupButton
        emailTxt = signupEmail
        passTxt = signupPass

        loginBtn.setOnClickListener {
            val i = Intent(this, signIn::class.java)
            startActivity(i)
        }
        signupBtn.setOnClickListener {
            if (emailTxt.text.toString().isEmpty()) {
                emailTxt.error = "Fill this field"
                emailTxt.requestFocus()
            } else if (passTxt.text.toString().isEmpty()) {
                passTxt.error = "Fill this field"
                passTxt.requestFocus()
            }else {
                auth.createUserWithEmailAndPassword(emailTxt.text.toString(), passTxt.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Log.d("tag", "createUser:Success")
                            var i = Intent(this, home::class.java)
                            startActivity(i)                        } else {
                            Log.d("tag", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}