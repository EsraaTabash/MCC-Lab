package com.example.firebase2.activityLab2

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase2.R
import com.example.firebase2.ass2.CategoryAdapter
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_realtime.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class realtime : AppCompatActivity() {

    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var senderUid: String
    private lateinit var receiverUid: String
    private lateinit var messageList: ArrayList<Message>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime)

        senderUid = "6fLVJKl7wTYQ5jd9p4QkphnvRG72"
        receiverUid = "DAhsAfVcIuMH2hA4MRuflLStPBI3"

        messagesRecyclerView = messages_recycler_view
        messageEditText = message_type_input
        sendButton = send_button

        sendButton.setOnClickListener {
            var messageText = messageEditText.text.toString().trim()
            if (!messageText.isEmpty()) {
                sendMessage(messageText)
                messageEditText.setText("")
            }
        }
        messageList = arrayListOf<Message>()
        val realtimeAdapter = realtimeAdapter(this, messageList, senderUid)
        messagesRecyclerView.adapter = realtimeAdapter
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        FirebaseDatabase.getInstance().getReference("chat")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val msg = snapshot.getValue(Message::class.java)
                    if (msg != null) {
                        messageList.add(msg)
                        Log.d("esr", "msg added to list >>2")
                    }
                    realtimeAdapter.notifyItemInserted(messageList.size - 1)
                    messagesRecyclerView.scrollToPosition(messageList.size - 1)
                    Log.d("esr", "msg added to rv >>3")

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(msg: String) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val obj = Message(
            msg,
            senderUid,
            receiverUid,
            LocalDateTime.now().format(formatter).toString()
        )
        FirebaseDatabase.getInstance().getReference("chat").push().setValue(obj)
        Log.d("esr", "value pushed up >>1")
    }

}