package com.jlillioja.uchicagoses.model

import com.google.firebase.database.DataSnapshot
import com.jlillioja.uchicagoses.extensions.toString
import java.util.Date

data class Announcement(val text: String, val time: Date) {
    companion object {
        fun fromData(dataSnapshot: DataSnapshot): Announcement? {
            val text = dataSnapshot.child("text").value as? String
            val timestamp = dataSnapshot.child("time").value as? Long
            val time = timestamp?.let { Date(it / 1000) }

            return if (text != null && time != null) Announcement(text, time) else null
        }
    }

    override fun toString(): String {
        return "${time.toString("MMM dd, h:mm a")}\n$text"
    }
}