package com.fraime.android.picture.data.repository

import android.widget.TextView
import com.fraime.android.picture.domain.repository.ChatRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatRepositoryImpl :
    ChatRepository {

    private val database = Firebase.database
    private val dRef = database.getReference("messages")

    override fun sendMessage(text: String) {
        dRef.setValue(text)
    }

    fun displayChangedText(textView: TextView) {
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                textView.append("\n")
                textView.append(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}