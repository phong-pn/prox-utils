package com.proxglobal.sale.utils

import android.os.Bundle
import com.google.firebase.analytics.ktx.ParametersBuilder
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

fun logFirebaseEvent(eventName: String, bundle: Bundle? = null) {
    Firebase.analytics.logEvent(eventName, bundle)
}

fun logFirebaseEvent(eventName: String, paramsBuilder: ParametersBuilder.() -> Unit) {
    Firebase.analytics.logEvent(eventName, paramsBuilder)
}