package com.jlillioja.uchicagoses

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_event_detail.*

class EventDetailActivity : AppCompatActivity() {

    lateinit var event: Event

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
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
