package com.veasnachhom.androidzoo.ui.widget

import android.os.Handler
import android.os.Looper
import android.view.View

class DelayClickListener : View.OnClickListener {

    private var isEventFired: Boolean = false
    private var onDelayClick: (View?) -> Unit = {}

    override fun onClick(p0: View?) {
        if (!isEventFired) {
            isEventFired = true
            onDelayClick.invoke(p0)
            Handler(Looper.getMainLooper()).postDelayed({
                isEventFired = false
            }, DELAY_INTERVAL)
        }
    }

    fun onDelayClick(listener: (View?) -> Unit): DelayClickListener {
        onDelayClick = listener
        return this
    }

    companion object {
        private const val DELAY_INTERVAL = 300L
    }
}