package com.veasnachhom.androidzoo.adapter

import android.text.TextUtils
import androidx.recyclerview.widget.DiffUtil
import com.veasnachhom.androidzoo.dataModel.BaseDiffModel

/*
    The sub class of BaseDiffModel must be data class for comparing the content of object to work
    correctly.
 */
open class Diff<D : BaseDiffModel>(
    private val oldList: List<D>, private val newList: List<D>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = TextUtils.equals(
        oldList[oldItemPosition].getItemId(), newList[newItemPosition].getItemId()
    )
}