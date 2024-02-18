package com.example.overlappingrecyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Rahul Ray.
 * Version 1.0.0 on 17,February,2024
 */
class OverlappingDecoration: RecyclerView.ItemDecoration() {

    private val overlapPercentage = 15

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val width = view.layoutParams.width

        val widthPercentage = (overlapPercentage * width * -1) / 100

        val position = parent.getChildAdapterPosition(view)

        val isReversed = (parent.layoutManager as? LinearLayoutManager)?.reverseLayout ?: false

        if (position == 0) {
           return
        } else {
            if (isReversed) {
                outRect.set(0, 0, widthPercentage, 0)
            } else {
                outRect.set(widthPercentage, 0, 0, 0)
            }
        }

    }

}