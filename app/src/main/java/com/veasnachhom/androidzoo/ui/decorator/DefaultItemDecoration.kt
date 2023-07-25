package com.veasnachhom.androidzoo.ui.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DefaultItemDecoration(private val horizontalSpace: Int, private val verticalSpace: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val childLayoutPosition = parent.getChildLayoutPosition(view)
        if (childLayoutPosition == 0) {
            outRect.top = verticalSpace
        }
        outRect.bottom = verticalSpace
        outRect.left = horizontalSpace
        outRect.right = horizontalSpace
    }
}