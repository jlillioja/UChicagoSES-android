package com.jlillioja.uchicagoses.model

import android.os.Parcelable
import com.google.firebase.database.DataSnapshot
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Event(val name: String?,
                 val location: String?,
                 val time: Date?,
                 val description: String?,
                 val link: URL?,
                 val key: String): Parcelable {

    override fun toString(): String {
        return "$name\n$formattedTime"
    }

    @IgnoredOnParcel
    val formattedTime: String? = {
        if (time != null) {
            val formatter = SimpleDateFormat("MMM dd, h:mm a")
            formatter.timeZone = TimeZone.getTimeZone("America/Chicago")
            formatter.format(time)
        } else null
    }()

    companion object {
        fun fromData(data: DataSnapshot): Event? {
            val name = data.child("name").value as? String
            if (name.isNullOrBlank()) return null
            val location = data.child("location").value as? String
            val urlString = data.child("link").value as? String
            val description = data.child("description").value as? String
            val timestamp = data.child("time").value as? Long
            val date = timestamp?.let { Date(it*1000) }

            val link = try {
                URL(urlString)
            } catch (error: Exception) {
                null
            }
            return Event(name, location, date, description, link, data.key)
        }
    }
}