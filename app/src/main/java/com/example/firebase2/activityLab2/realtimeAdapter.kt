package com.example.firebase2.activityLab2

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase2.R
import com.example.firebase2.ass2.Categories
import kotlinx.android.synthetic.main.note_item.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class realtimeAdapter(
    var activity: Activity,
    var data: ArrayList<Message>,
    var currentUserUid: String
) : RecyclerView.Adapter<realtimeAdapter.myViewHolder>() {

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.message_text)
        val messageTime: TextView = itemView.findViewById(R.id.message_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.message_item, parent, false)
        return myViewHolder(root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val message = data[position]
        holder.messageText.text = message.text
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        holder.messageTime.text = LocalDateTime.now().format(formatter).toString()
        val layoutParams = holder.messageText.layoutParams as LinearLayout.LayoutParams
        layoutParams.gravity =
            if (message.senderId == currentUserUid) Gravity.START else Gravity.END
        holder.messageText.layoutParams = layoutParams


    }

    override fun getItemCount(): Int {
        return data.size
    }


}
