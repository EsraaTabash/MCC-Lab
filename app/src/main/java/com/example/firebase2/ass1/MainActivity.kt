package com.example.firebase2.ass1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase2.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var FBFS: FirebaseFirestore
    lateinit var list:ArrayList<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FBFS = Firebase.firestore
        list = ArrayList()

        addBtn.setOnClickListener{
            startActivity(Intent(this, add::class.java))
        }
        FBFS.collection("note")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(
                        Model(
                            document.getString("name"),
                            document.getString("content"),
                            document.getLong("num")?.toInt()
                        )
                    )
                val rv = findViewById<RecyclerView>(R.id.recyclerView)
                val A = MyAdapter(this, list)
                rv.adapter = A
                rv.layoutManager = LinearLayoutManager(this)
                    Log.e("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error getting documents.", exception)
            }


    }
}