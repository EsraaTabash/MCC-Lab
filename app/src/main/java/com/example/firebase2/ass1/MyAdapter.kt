package com.example.firebase2.ass1

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase2.R
import kotlinx.android.synthetic.main.note_item.view.*

class MyAdapter (var activity: Activity, var data :ArrayList<Model>)
    : RecyclerView.Adapter <MyAdapter.myViewHolder>() {

    class myViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val name=itemView.tv_noteName
        val content=itemView.tv_noteContent
        //val num=itemView.tv_noteNum
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
            val root=LayoutInflater.from(activity).inflate(R.layout.note_item,parent,false)
            return myViewHolder(root)
        }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text=data[position].name
        holder.content.text=data[position].content
        //holder.num.text=data[position].num.toString()
    }
    override fun getItemCount(): Int {
        return data.size
    }


}
