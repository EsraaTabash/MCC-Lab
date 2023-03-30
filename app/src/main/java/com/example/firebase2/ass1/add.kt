package com.example.firebase2.ass1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase2.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class add : AppCompatActivity() {
    lateinit var FBFS: FirebaseFirestore
    lateinit var addBtn: Button
    lateinit var nameTxt: EditText
    lateinit var contentTxt: EditText
    lateinit var numTxt: EditText

//    var channel_id: String = "123"
//    var id: Int = 1
//    var manager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add)
        FBFS = Firebase.firestore


        addBtn = findViewById<Button>(R.id.addBtn)
        nameTxt = findViewById<EditText>(R.id.nameTxt)
        contentTxt = findViewById<EditText>(R.id.contentTxt)
        numTxt = findViewById<EditText>(R.id.numTxt)

        addBtn.setOnClickListener {
            if(nameTxt.text.toString().isEmpty()) {
                nameTxt.error = "Fill this field"
                nameTxt.requestFocus()
            }else if(contentTxt.text.toString().isEmpty()){
                contentTxt.error = "Fill this field"
                contentTxt.requestFocus()
            }else if(numTxt.text.toString().isEmpty()){
                numTxt.error = "Fill this field"
                numTxt.requestFocus()
            }else if(numTxt.text.toString().toInt() !== contentTxt.length()){
                contentTxt.error = "write in the right range"

            }else {
            var num=numTxt.text.toString().toInt()
            var newContent=contentTxt.text.substring(0,num)
            Log.e("tag",num.toString())
            Log.e("tag",newContent)
            addNewNote(nameTxt.text.toString(), newContent ,num)
        }}
    }

    private fun addNewNote(name: String, content: String, num: Int) {
        var note = hashMapOf(
            "name" to name,
            "content" to content,
            "num" to num
        )
        FBFS.collection("note")
            .add(note)
            .addOnSuccessListener { documentReference ->
                Log.e("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                startActivity(Intent(this, MainActivity::class.java))

            }
            .addOnFailureListener { e ->
                Log.e("TAG", "Error adding document",e)
            }

    }

    }
