package com.jlillioja.uchicagoses

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val events: MutableList<Event> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eventsAdapter = ArrayAdapter(this, R.layout.event_cell, events)
        listView.adapter = eventsAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this@MainActivity, EventDetailActivity::class.java)
            intent.putExtra("event", events[position])
            startActivity(intent)
        }

        FirebaseDatabase.getInstance().reference.child("events").addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(data: DataSnapshot?, previous: String?) {
                if (data != null) {
                    val event = Event.fromData(data)
                    if (event != null) {
                        events.add(event)
                        events.sortBy { it.time }
                    }
                    eventsAdapter.notifyDataSetChanged()
                }
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onCancelled(p0: DatabaseError?) {}
        })
    }
}
