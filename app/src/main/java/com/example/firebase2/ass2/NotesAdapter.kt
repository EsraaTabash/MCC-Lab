package com.example.firebase2.ass2

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase2.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.notes_item.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class NotesAdapter (var activity: Activity, var data :ArrayList<Model2>)
    : RecyclerView.Adapter <NotesAdapter.myViewHolder>() {
    var analytics: FirebaseAnalytics =  Firebase.analytics
    var FBFS: FirebaseFirestore = Firebase.firestore

    class myViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val name=itemView.notes_name
        val img=itemView.notes_img
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
            val root=LayoutInflater.from(activity).inflate(R.layout.notes_item,parent,false)
            return myViewHolder(root)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text = data[position].name
        Picasso.get().load(data[position].image).into(holder.img)
        holder.itemView.setOnClickListener {
            selectContentNotes(data[position].id.toString(),data[position].name!!)
            val intent2 = Intent(activity, Details::class.java)
            intent2.putExtra("image", data[position].image)
            intent2.putExtra("name", data[position].name)
            intent2.putExtra("content", data[position].content)
            intent2.putExtra("num", data[position].num)
            intent2.putExtra("id", data[position].id)
            val formatter = DateTimeFormatter.ofPattern("mm ss")
            val end = LocalDateTime.now().format(formatter)
            Log.e("time","Note time begin : "+Notes.begin.toString())
            Log.e("time","Note time end : "+end.toString())
            var t1=Notes.begin.toString()
            var t2=end.toString()
            if(t1.substring(0,2).toInt() == t2.substring(0,2).toInt()){
                var duration=t2.substring(3).toInt() - t1.substring(3).toInt()
                Log.e("time","Note time duration : "+duration.toString()+" sec")
                addTime(duration.toString()+" sec")
            }else if((t2.substring(0,2).toInt() - t1.substring(0,2).toInt()) == 1) {
                var duration = (60 - t1.substring(3).toInt() )+ t2.substring(3).toInt()
                Log.e("time", "Note time duration : " + duration.toString() + " sec")
                addTime(duration.toString()+" sec")
            }else if((t2.substring(0,2).toInt() - t1.substring(0,2).toInt()) > 1){
                var durationMin=t2.substring(0,2).toInt() - t1.substring(0,2).toInt()
                var durationSec=(60 - t1.substring(3).toInt() )+ t2.substring(3).toInt()
                if(durationSec>60) {
                    durationSec = durationSec - 60
                    durationMin = durationMin + 1
                    Log.e("time", "Note time duration : " + durationMin.toString() + " min " + durationSec.toString() + " sec")
                    addTime(durationMin.toString() + " min " + durationSec.toString() + " sec")
                }else{
                    Log.e("time", "Note time duration : " + durationMin.toString() + " min " + durationSec.toString() + " sec")
                    addTime(durationMin.toString() + " min " + durationSec.toString() + " sec")
                }
            }else{
                Log.e("time","no")
            }
            activity.startActivity(intent2)
        }
        }
    override fun getItemCount(): Int {
        return data.size
    }
    fun selectContentNotes(id:String,contentType:String){
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
            param(FirebaseAnalytics.Param.ITEM_ID, id);
            param(FirebaseAnalytics.Param.CONTENT_TYPE,contentType);
        }
    }
    fun addTime(time:String) {
        var timeDoc = hashMapOf(
            "Name" to "NotesPage",
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




