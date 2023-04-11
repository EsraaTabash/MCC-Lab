package com.example.firebase2.activityLab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebase2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class home : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val currentUserEmail = auth.currentUser?.email
        val currentUserUid= auth.currentUser?.uid
        profileEmailTxt.text = currentUserEmail
        profileUidTxt.text = currentUserUid
        signoutButton.setOnClickListener{
            Firebase.auth.signOut()
            var i = Intent(this, signIn::class.java)
            startActivity(i)
        }

    }
}