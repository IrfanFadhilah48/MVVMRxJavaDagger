@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.irfan.mvvmrxjavadagger.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun parseDate(tanggal: String): String? {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd")
    val parseFormat = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
    var formattedDate: String? = null
    try {
        val convertedDate = originalFormat.parse(tanggal)
        formattedDate = parseFormat.format(convertedDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return formattedDate
}