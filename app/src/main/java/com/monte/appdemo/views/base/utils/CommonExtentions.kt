package com.monte.appdemo.views.base.utils

import android.content.res.Resources

val Int.toPx: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
