package com.jlillioja.uchicagoses.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toString(format: String) : String {
    val formatter = SimpleDateFormat(format)
    formatter.timeZone = TimeZone.getTimeZone("America/Chicago")
    return formatter.format(time)
}