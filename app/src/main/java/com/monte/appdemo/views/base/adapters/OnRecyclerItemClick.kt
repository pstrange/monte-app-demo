package com.monte.appdemo.views.base.adapters

import android.view.View

interface OnRecyclerItemClick<T> {
    fun onItemClick(view: View?, event: Event<T?>?, position: Int)
}