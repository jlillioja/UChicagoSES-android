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

    var eventCount = 0

    private val events: MutableList<Event> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eventsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, events)
        listView.adapter = eventsAdapter
        listView.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(this@MainActivity, EventDetailActivity::class.java)
                intent.putExtra("event", events[position])
                startActivity(intent)
            }
        }

        val database = FirebaseDatabase.getInstance().reference
//        val eventInfoReference = database.child("eventInfo").child("eventCount").addValueEventListener(object: ValueEventListener {
//            override fun onCancelled(p0: DatabaseError?) {
//
//            }
//
//            override fun onDataChange(data: DataSnapshot?) {
//                eventCount = data?.value?.toString()?.toInt() ?: 0
//                this@MainActivity.reload()
//            }
//
//        })
        database.child("events").addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(data: DataSnapshot?, previous: String?) {
                if (data != null) {
                    events.add(Event.fromData(data))
                    eventsAdapter.notifyDataSetChanged()
                }
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onCancelled(p0: DatabaseError?) {}
        })
    }

//    private fun reload() {
//        val eventsReference = FirebaseDatabase.getInstance().reference.child("events")
//        if (eventCount < 1) return
//        for (i in 0 until eventCount) {
//            eventsReference.child(i.toString())
//        }
//    }
}
