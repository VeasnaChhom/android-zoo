package com.veasnachhom.androidzoo.ui.widget

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView

class SquareShapableImaeView(private val cntext: Context, private val attr: AttributeSet?) :
    ShapeableImageView(cntext, attr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //Make view's size square base on the width
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}