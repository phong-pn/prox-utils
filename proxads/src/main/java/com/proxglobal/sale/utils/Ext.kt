package com.proxglobal.sale.utils

import android.content.res.Resources
import android.util.Log

val TAG = "LOG_PROXGLOBAL"
fun Any.logd(tag: String = TAG) {
    Log.d(TAG, toString())
}

fun Any.loge(tag: String = TAG) {
    Log.e(TAG, toString())
}

fun Any.logw(tag: String = TAG) {
    Log.w(TAG, toString())
}

val Number.dp: Float
    get() {
        val scale = Resources.getSystem().displayMetrics.density
        return this.toFloat() * scale + 0.5f
    }