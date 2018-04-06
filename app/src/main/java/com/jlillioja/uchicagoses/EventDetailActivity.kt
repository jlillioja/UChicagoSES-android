package com.jlillioja.uchicagoses

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.util.Linkify
import android.view.MenuItem
import android.widget.TextView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.jlillioja.uchicagoses.extensions.toString
import com.jlillioja.uchicagoses.model.Comment
import com.jlillioja.uchicagoses.model.Event
import kotlinx.android.synthetic.main.activity_event_detail.*
import java.util.*

class EventDetailActivity : AppCompatActivity() {

    lateinit var event: Event
    val comments: MutableList<Comment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getParcelableExtra("event") as Event

        name.text = event.name ?: ""

        time.text = "Time: ${event.formattedTime ?: ""}"

        location.text = "Location: ${event.location ?: "TBD"}"
        Linkify.addLinks(location, Linkify.MAP_ADDRESSES)

        description.text = event.description

        if (event.link != null) {
            slidesLink.text = event.link.toString()
            Linkify.addLinks(slidesLink, Linkify.WEB_URLS)
        }

        val commentsRef = FirebaseDatabase.getInstance().reference.child("events/${event.key}/comments")
        submitButton.setOnClickListener {
            if (!commentEntry.text.isNullOrBlank()) {
                commentsRef.push().setValue(hashMapOf("text" to commentEntry.text.toString(), "time" to (Date().time / 1000)))
                commentEntry.editableText.clear()
            }
        }

        commentsRef.addChildEventListener(
                object : ChildEventListener {
                    override fun onChildAdded(data: DataSnapshot?, previous: String?) {
                        val comment = data?.let { Comment.fromData(it) }
                        if (comment != null) {
                            addComment(comment)
                        }
                    }

                    override fun onCancelled(p0: DatabaseError?) {}

                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

                    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}

                    override fun onChildRemoved(p0: DataSnapshot?) {}

                })
    }

    private fun addComment(comment: Comment) {
        comments.add(comment)
        comments.sortByDescending { it.time }
        val commentView = layoutInflater.inflate(R.layout.comment_cell, commentsList, false)
        commentView.findViewById<TextView>(R.id.date).text = comment.time.toString("MMM dd, h:mm a")
        commentView.findViewById<TextView>(R.id.text).text = comment.text
        commentsList.addView(commentView, comments.indexOf(comment))
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
