package com.example.firebase2.ass2

import android.net.Uri
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
import com.google.firebase.analytics.ktx.FirebaseAnalyticsKtxRegistrar
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_categories.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Categories : AppCompatActivity() {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter = DateTimeFormatter.ofPattern("mm ss")
        @RequiresApi(Build.VERSION_CODES.O)
        val begin = LocalDateTime.now().format(formatter)
    }
    lateinit var FBFS: FirebaseFirestore
    lateinit var list: ArrayList<Model1>
    lateinit var progress: ProgressBar
    lateinit var analytics: FirebaseAnalytics
    lateinit var storageRef :StorageReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        FBFS = Firebase.firestore
        analytics = Firebase.analytics
        list = ArrayList()
        progress = progressCategory
        progress.visibility = View.VISIBLE
        storageRef = FirebaseStorage.getInstance().reference
        screenTrackCategory("CategoriesClass","categories")
        FBFS.collection("category")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(
                        Model1(
                            document.getLong("id")?.toInt(),
                            document.getString("name"),
                            document.getString("image")
                        )
                    )
                    val rv = findViewById<RecyclerView>(R.id.recyclerView2)
                    val A = CategoryAdapter(this, list)
                    rv.adapter = A
                    rv.layoutManager = LinearLayoutManager(this)
                    progress.visibility = View.GONE
                    //Log.e("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error getting documents.", exception)
            }


    }
    fun screenTrackCategory(screenClass:String,screenName:String) {
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass);
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        }
    }



}

