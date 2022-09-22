package com.alwan.plantdisease.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val spaceHeight: Int,
    private var startEndHeight: Int = 0,
    private val isHorizontal: Boolean = false,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        if (startEndHeight == 0) startEndHeight = spaceHeight
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                if (isHorizontal)
                    left = startEndHeight
                else
                    top = startEndHeight
            }

            if (parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1)) {
                if (isHorizontal) {
                    right = startEndHeight
                    top = spaceHeight
                    bottom = spaceHeight
                } else {
                    bottom = startEndHeight
                    right = spaceHeight
                    left = spaceHeight
                }
            } else {
                if (isHorizontal) {
                    right = spaceHeight
                    top = spaceHeight
                    bottom = spaceHeight
                } else {
                    bottom = spaceHeight
                    right = spaceHeight
                    left = spaceHeight
                }

            }
        }
    }
}