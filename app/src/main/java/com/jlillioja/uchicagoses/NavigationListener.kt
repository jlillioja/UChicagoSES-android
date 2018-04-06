package com.jlillioja.uchicagoses

import com.jlillioja.uchicagoses.model.Event

interface NavigationListener {
    fun navigateToEventDetail(event: Event)
}