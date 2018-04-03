package com.jlillioja.uchicagoses

import android.os.Parcel
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
                 val slidesLink: URL?): Parcelable {

    override fun toString(): String {
        return "$name\n$formattedTime"
    }

    @IgnoredOnParcel
    val formattedTime: String? = {
        if (time != null) {
            val formatter = SimpleDateFormat.getDateTimeInstance()
            formatter.timeZone = TimeZone.getTimeZone("America/Chicago")
            formatter.format(time)
        } else null
    }()

    companion object {
        fun fromData(data: DataSnapshot): Event {
            val name = data.child("name").value as String
            val location = data.child("location").value as String
            val slidesLinkString = data.child("slidesLink").value as String
            val timestamp = data.child("time").value as Long

            val slidesLink = try {
                URL(slidesLinkString)
            } catch (error: Exception) {
                null
            }
            return Event(name, location, Date(timestamp * 1000), slidesLink)
        }
    }
}