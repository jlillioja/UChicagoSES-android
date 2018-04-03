package com.jlillioja.uchicagoses

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_event_detail.*

class EventDetailActivity : AppCompatActivity() {

    lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getParcelableExtra("event") as Event

        name.text = event.name ?: ""
        time.text = event.formattedTime ?: ""
        location.text = event.location ?: ""
//        location.autoLinkMask = 8
        Linkify.addLinks(location, Linkify.MAP_ADDRESSES)
        location.visibility = View.VISIBLE

        if (event.slidesLink != null) {
            slidesLink.text = event.slidesLink.toString()
            Linkify.addLinks(slidesLink, Linkify.WEB_URLS)
//            slidesLink.autoLinkMask = 1
            slidesLink.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
