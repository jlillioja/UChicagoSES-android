package com.jlillioja.uchicagoses.model

import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.math.roundToLong

data class Comment(val text: String, val time: Date) {
    companion object {
        fun fromData(data: DataSnapshot): Comment? {
            val text = data.child("text").value as? String
            val timestamp = data.child("time").value as? Long
            val date = timestamp?.let { Date(it*1000) }

            return if (text != null && date != null) {
                Comment(text, date)
            } else {
                null
            }
        }
    }
}