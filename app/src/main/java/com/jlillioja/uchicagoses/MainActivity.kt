package com.jlillioja.uchicagoses

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.jlillioja.uchicagoses.model.Event


class MainActivity : AppCompatActivity(), NavigationListener {

    private val eventListFragment = EventListFragment.newInstance(this)
    private val announcementsFragment = AnnouncementsFragment()
    private val contactFragment = ContactFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Socioeconomic Empowerment Summit"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)

        drawer.setNavigationItemSelectedListener {
            it.isChecked = true
            drawer_layout.closeDrawers()
            when (it.itemId) {
                R.id.announcements -> {
                    navigateTo(announcementsFragment)
                    true
                }
                R.id.schedule -> {
                    navigateTo(eventListFragment)
                    true
                }
                R.id.contact -> {
                    navigateTo(contactFragment)
                    true
                }
                else -> false
            }
        }

        navigateTo(announcementsFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun navigateToEventDetail(event: Event) {
        val intent = Intent(this, EventDetailActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(fragmentContainer.id, fragment)
                .commit()
    }
}
