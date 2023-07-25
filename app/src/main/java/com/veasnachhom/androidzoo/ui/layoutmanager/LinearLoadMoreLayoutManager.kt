package com.veasnachhom.androidzoo.ui.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class LinearLoadMoreLayoutManager(context: Context?) : LinearLayoutManager(context) {

    private var mIsLoadingMore = false
    private var mShouldDetectLoadMore: Boolean? = false
    private var onReachedLoadMoreBottom: () -> Unit = {}
    private var onReachedLoadMoreByDefault: () -> Unit = {}

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, state: RecyclerView.State?): Int {
        if (mShouldDetectLoadMore == true && !mIsLoadingMore) {
            if (isReachedLastItem()) {
                mIsLoadingMore = true
                onReachedLoadMoreBottom.invoke()
            }
        }

        return super.scrollVerticallyBy(dy, recycler, state)
    }

    fun checkLToInvokeReachLoadMoreByDefaultIfNecessary() {
        //Log.i(TAG, "checkLToInvokeReachLoadMoreByDefaultIfNecessary: mIsLoadingMore ==> " + mIsLoadingMore);
        if (isReachedLastItem() && !mIsLoadingMore) {
            //Log.i(TAG, "onReachedLoadMoreByDefault");
            mIsLoadingMore = true
            onReachedLoadMoreByDefault.invoke()
        }
    }

    private fun isReachedLastItem(): Boolean {
        return findFirstVisibleItemPosition() + childCount >= itemCount
    }

    fun loadingFinished() {
        mIsLoadingMore = false
    }

    fun setShouldDetectLoadMore(shouldDetectLoadMore: Boolean? = false) {
        mShouldDetectLoadMore = shouldDetectLoadMore
    }

    fun onReachedLoadMoreBottom(callback: () -> Unit) {
        onReachedLoadMoreBottom = callback
    }

    fun onReachedLoadMoreByDefault(callback: () -> Unit) {
        onReachedLoadMoreByDefault = callback
    }
}