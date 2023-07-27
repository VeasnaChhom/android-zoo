package com.veasnachhom.androidzoo.utility

import android.graphics.Color
import android.graphics.Paint
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.annotation.StringRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.veasnachhom.androidzoo.ui.widget.DelayClickListener
import com.veasnachhom.androidzoo.utility.ExtensionUtil.loadImageView


object BindingUtil {

    @JvmStatic
    @BindingAdapter("editorChangeListener")
    fun setEditorChangeListener(
        editText: TextInputEditText, editorActionListener: OnEditorActionListener
    ) {
        editText.setOnEditorActionListener(editorActionListener)
    }

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
    @BindingAdapter("setCursorToEnd")
    fun setCursorToEnd(inputText: EditText, enable: Boolean) {
        if (enable) {
            inputText.setSelection(inputText.text.length)
        }
    }

    @JvmStatic
    @BindingAdapter("loadImageFromUrl")
    fun loadImageFromUrl(imageView: ImageView, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            url?.let {
                imageView.loadImageView(it)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setBackgroundTint")
    fun setBackgroundTint(view: View, color: Int) {
        view.background.apply {
            DrawableCompat.setTint(this, color)
        }
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