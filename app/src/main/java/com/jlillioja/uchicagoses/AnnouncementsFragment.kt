package com.jlillioja.uchicagoses

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.jlillioja.uchicagoses.extensions.toString
import com.jlillioja.uchicagoses.model.Announcement
import com.jlillioja.uchicagoses.model.Event
import kotlinx.android.synthetic.main.announcement_cell.view.*
import kotlinx.android.synthetic.main.fragment_announcements.*
import kotlinx.android.synthetic.main.fragment_event_list.*


class AnnouncementsFragment: Fragment() {

    var announcements: MutableList<Announcement> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        announcements.clear()
        FirebaseDatabase.getInstance().reference.child("announcements").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(data: DataSnapshot?, previous: String?) {
                if (data != null) {
                    val announcement = Announcement.fromData(data)
                    if (announcement != null) {
                        addAnnouncement(announcement)
                    }
                }
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onCancelled(p0: DatabaseError?) {}
        })
    }

    private fun addAnnouncement(announcement: Announcement) {
        announcements.add(announcement)
        announcements.sortByDescending { it.time }
        val cell = layoutInflater.inflate(R.layout.announcement_cell, announcementsList, false)
        cell.date.text = announcement.time.toString("MMM dd, h:mm a")
        cell.text.text = announcement.text
        announcementsList.addView(cell, announcements.indexOf(announcement))
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_announcements, container, false)
}