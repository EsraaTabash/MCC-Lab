package com.example.firebase2.ass2

import android.content.Intent
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
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.back
import kotlinx.android.synthetic.main.activity_notes.*
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class Details : AppCompatActivity() {
    lateinit var progress: ProgressBar
    lateinit var analytics: FirebaseAnalytics
    var FBFS: FirebaseFirestore = Firebase.firestore
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter = DateTimeFormatter.ofPattern("mm ss")
        @RequiresApi(Build.VERSION_CODES.O)
        val begin = LocalDateTime.now().format(formatter)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val begin = Date().time
        setContentView(R.layout.activity_details)
        progress = progressDetails
        progress.visibility = View.VISIBLE
        analytics = Firebase.analytics

        var detailsImage=Image
        var detailsName=Name
        var detailsContent=Content
        var detailsNum=Num

        val image = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val content = intent.getStringExtra("content")
        val num = intent.getIntExtra("num", 0)
        val id = intent.getIntExtra("id",0)
        Picasso.get().load(image).into(detailsImage)
        detailsName.text=name
        detailsContent.text=content
        detailsNum.text=num.toString()
        progress.visibility = View.GONE


        back.setOnClickListener{
            val intent = Intent(this, Notes::class.java)
            intent.putExtra("id",id)
            val formatter = DateTimeFormatter.ofPattern("mm ss")
            val end = LocalDateTime.now().format(formatter)
            Log.e("time","Details time begin : "+Details.begin.toString())
            Log.e("time","Details time end : "+end.toString())
            var t1=Details.begin.toString()
            var t2=end.toString()
            if(t1.substring(0,2).toInt() == t2.substring(0,2).toInt()){
                var duration=t2.substring(3).toInt() - t1.substring(3).toInt()
                Log.e("time","Details time duration : "+duration.toString()+" sec")
                addTime(duration.toString()+" sec")
            }else if((t2.substring(0,2).toInt() - t1.substring(0,2).toInt()) == 1) {
                var duration = (60 - t1.substring(3).toInt() )+ t2.substring(3).toInt()
                Log.e("time", "Details time duration : " + duration.toString() + " sec")
                addTime(duration.toString()+" sec")
            }else if((t2.substring(0,2).toInt() - t1.substring(0,2).toInt()) > 1){
                var durationMin=t2.substring(0,2).toInt() - t1.substring(0,2).toInt()
                var durationSec=(60 - t1.substring(3).toInt() )+ t2.substring(3).toInt()
                if(durationSec>60) {
                    durationSec = durationSec - 60
                    durationMin = durationMin + 1
                    Log.e("time", "Details time duration : " + durationMin.toString() + " min " + durationSec.toString() + " sec")
                    addTime(durationMin.toString() + " min " + durationSec.toString() + " sec")
                }else{
                    Log.e("time", "Details time duration : " + durationMin.toString() + " min " + durationSec.toString() + " sec")
               addTime(durationMin.toString() + " min " + durationSec.toString() + " sec")
                }
            }else{
                Log.e("time","no")
            }
            startActivity(intent)

        }
}
    fun addTime(time:String) {
        var timeDoc = hashMapOf(
            "Name" to "DetailsPage",
            "Time" to time,
            "User" to "EsraaTabash2002"
        )
        FBFS.collection("time")
            .add(timeDoc)
            .addOnSuccessListener { documentReference ->
                Log.e("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "Error adding document",e)
            }

    }

}