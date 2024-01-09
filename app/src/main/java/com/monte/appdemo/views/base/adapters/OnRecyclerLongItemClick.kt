package com.monte.appdemo.views.base.adapters

import android.view.View

interface OnRecyclerLongItemClick<T> {
    fun onLongItemClick(view: View?, item: T?, position: Int) : Boolean
}