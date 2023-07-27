package com.veasnachhom.androidzoo.utility

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.veasnachhom.androidzoo.ui.widget.DelayClickListener


object BindingUtil {

    @JvmStatic
    @BindingAdapter(value = ["isRefreshing", "onRefreshListener"], requireAll = true)
    fun setSwipeActionListener(
        swipeRefreshLayout: SwipeRefreshLayout,
        isRefreshing: Boolean,
        onRefreshListener: SwipeRefreshLayout.OnRefreshListener
    ) {
        swipeRefreshLayout.isRefreshing = isRefreshing
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
    }

    @JvmStatic
    @BindingAdapter("setDelayClickListener")
    fun setDelayClickListener(v: View, clickListener: View.OnClickListener) {
        v.setOnClickListener(DelayClickListener().onDelayClick { clickListener.onClick(v) })
    }

    @JvmStatic
    @BindingAdapter("setDisplayShimmerEffect")
    fun setDisplayShimmerEffect(v: ShimmerFrameLayout, display: Boolean) {
        if (display) v.startShimmer() else v.stopShimmer()
    }

    @JvmStatic
    @BindingAdapter(value = ["stringResourceId", "argument"], requireAll = true)
    fun setCustomResourceString(
        textView: TextView, @StringRes stringResourceId: Int, argument: String
    ) {
        textView.text = textView.context.getString(stringResourceId, argument)
    }

    @JvmStatic
    @BindingAdapter("setTextUrl")
    fun setTextUrl(textView: TextView, url: String) {
        textView.text = url
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}