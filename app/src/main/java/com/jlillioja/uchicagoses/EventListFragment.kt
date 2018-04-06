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
import com.jlillioja.uchicagoses.model.Event
import kotlinx.android.synthetic.main.fragment_event_list.*

class EventListFragment : Fragment() {

    var events: MutableList<Event> = mutableListOf()
    var listener: NavigationListener? = null

    lateinit var eventsAdapter: ArrayAdapter<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventsAdapter = ArrayAdapter(context, R.layout.event_cell, events)
        FirebaseDatabase.getInstance().reference.child("events").addChildEventListener(object : ChildEventListener {
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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_event_list, container, false)

    override fun onResume() {
        super.onResume()

        listView.adapter = eventsAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            listener?.navigateToEventDetail(events[position])
        }
    }

    companion object {
        fun newInstance(listener: NavigationListener): EventListFragment {
            return EventListFragment().also { it.listener = listener }
        }
    }
}