package com.monte.appdemo.views.base.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
	val lookupSize: (position: Int) -> Int,
	val spanCount: Int,
	val spacing: Int,
	val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

	override fun getItemOffsets(
		outRect: Rect,
		view: View,
		parent: RecyclerView,
		state: RecyclerView.State
	) {
		val position = parent.getChildAdapterPosition(view) // item position
		val column = position % lookupSize(position) // item column
		if (includeEdge) {
			outRect.left =
				spacing - column * spacing / lookupSize(position) // spacing - column * ((1f / spanCount) * spacing)
			outRect.right =
				(column + 1) * spacing / lookupSize(position) // (column + 1) * ((1f / spanCount) * spacing)
			if (position < spanCount) { // top edge
				outRect.top = spacing
			}
			outRect.bottom = spacing // item bottom
		} else {
			outRect.left = column * spacing / lookupSize(position) // column * ((1f / spanCount) * spacing)
			outRect.right =
				spacing - (column + 1) * spacing / lookupSize(position) // spacing - (column + 1) * ((1f /    spanCount) * spacing)
			if (position >= spanCount) {
				outRect.top = spacing // item top
			}
		}
	}
}