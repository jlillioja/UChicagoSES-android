package com.jlillioja.uchicagoses

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import kotlinx.android.synthetic.main.activity_event_detail.*

class EventDetailActivity : AppCompatActivity() {

    lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        event = intent.getParcelableExtra("event") as Event

        name.text = event.name ?: ""
        time.text = event.formattedTime ?: ""
        location.text = event.location ?: ""
        if (event.slidesLink != null) {
            slidesLink.text = event.slidesLink.toString()
        }

        for (textview in listOf(name, time, location, slidesLink)) {
            Linkify.addLinks(textview, Linkify.ALL)
        }
    }
}
