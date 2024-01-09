package com.monte.appdemo.views.base.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FirstItemMarginDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		super.getItemOffsets(outRect, view, parent, state)
		val position = parent.getChildAdapterPosition(view)
		if (position == 0 && parent.layoutManager is LinearLayoutManager
			&& (parent.layoutManager as LinearLayoutManager).orientation == RecyclerView.HORIZONTAL) {
			outRect.left = margin
		}
	}
}