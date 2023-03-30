package com.example.firebase2.ass2

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase2.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_notes.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class Notes : AppCompatActivity() {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter = DateTimeFormatter.ofPattern("mm ss")
        @RequiresApi(Build.VERSION_CODES.O)
        val begin = LocalDateTime.now().format(formatter)
    }
    lateinit var FBFS: FirebaseFirestore
    lateinit var list:ArrayList<Model2>
    lateinit var progress: ProgressBar
    lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        analytics = Firebase.analytics
        progress = progressNotes
        progress.visibility = View.VISIBLE
        val id = intent.getIntExtra("id", 0)
        //Log.e("Tag", id.toString())

        if (id == 1) {
             firebasestoreClass(1)
             screenTrackNotes("HomeClass","home")
        } else if (id == 2) {
             firebasestoreClass(2)
            screenTrackNotes("UniversityClass","universityClass")
        } else if (id == 3) {
             firebasestoreClass(3)
            screenTrackNotes("WorkClass","work")
        }
        back.setOnClickListener{
            val i = Intent(this, Categories::class.java)
            startActivity(i)

        }
    }

    fun firebasestoreClass(id:Int){
        FBFS = Firebase.firestore
        list = ArrayList()
        FBFS.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (id == document.getLong("id")?.toInt()) {
                        list.add(
                            Model2(
                                document.getLong("id")?.toInt(),
                                document.getString("name"),
                                document.getString("image"),
                                document.getString("content"),
                                document.getLong("num")?.toInt()
                            )
                        )
                        val rv = findViewById<RecyclerView>(R.id.recyclerView3)
                        val A = NotesAdapter(this, list)
                        rv.adapter = A
                        rv.layoutManager = LinearLayoutManager(this)
                       //Log.e("TAG", "${document.id} => ${document.data}")
                    }
                }
                progress.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error getting documents.", exception)
            }


    }
    fun screenTrackNotes(screenClass:String,screenName:String) {
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass);
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        }
    }

}