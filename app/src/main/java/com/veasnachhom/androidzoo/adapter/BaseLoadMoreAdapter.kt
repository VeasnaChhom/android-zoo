package com.veasnachhom.androidzoo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.veasnachhom.androidzoo.dataModel.BaseDiffModel
import com.veasnachhom.androidzoo.databinding.ListLoadingItemBinding

abstract class BaseLoadMoreAdapter<D : BaseDiffModel>(protected val data: ArrayList<D> = arrayListOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var canLoadMore: Boolean = false
    private var isShowLoadMoreFailedState: Boolean = false
    private lateinit var onRetryLoadMore: () -> Unit

    final override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == TYPE_LOAD_MORE_ITEM) {
            val itemBinding =
                ListLoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadMoreViewHolder(itemBinding)
        } else {
            onCreateChildViewHolder(parent, viewType)
        }
    }

    final override fun getItemCount(): Int {
        return if (canLoadMore) data.size + 1 else data.size
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadMoreViewHolder) {
            holder.bindData(isShowLoadMoreFailedState) {
                onRetryLoadMore.invoke()
            }
        } else {
            onBindChildViewHolder(holder, position)
        }
    }

    open fun resetData(data2: List<D>, hasLoadMore: Boolean? = false) {
        canLoadMore = hasLoadMore == true
        data.clear()
        data.addAll(data2)
        notifyDataSetChanged()
    }

    open fun setRefreshData(data2: List<D>, hasLoadMore: Boolean? = false) {
        canLoadMore = hasLoadMore == true
        val personDiff = Diff(data, data2)
        val diffResult = DiffUtil.calculateDiff(personDiff)
        data.clear()
        data.addAll(data2)
        diffResult.dispatchUpdatesTo(this)
    }

    open fun addLoadMoreItem(data2: List<D>, hasMoreItem: Boolean? = false) {
        canLoadMore = hasMoreItem == true
        val lastItemCount = itemCount
        data.addAll(data2)
        notifyItemRangeInserted(lastItemCount + 1, data2.size)
    }

    fun removeLoadMoreLayout() {
        if (canLoadMore) {
            val lastItemCount = itemCount
            notifyItemRemoved(lastItemCount)
        }
    }

    abstract fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    override fun getItemViewType(position: Int): Int {
        if (canLoadMore) {
            return if (position == itemCount - 1) {
                TYPE_LOAD_MORE_ITEM
            } else {
                TYPE_NORMAL_ITEM
            }
        }

        return TYPE_NORMAL_ITEM
    }

    fun onRetryLoadMore(retry: () -> Unit) {
        onRetryLoadMore = retry
        isShowLoadMoreFailedState = false
    }

    fun onLoadMoreDataError() {
        isShowLoadMoreFailedState = true
    }

    class LoadMoreViewHolder(private val binding: ListLoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(isShowError: Boolean? = false, onRetryClicked: () -> Unit) {
            updateViewStatus(isShowError)
            binding.buttonRetry.setOnClickListener {
                updateViewStatus(false)
                onRetryClicked.invoke()
            }
        }

        private fun updateViewStatus(isShowError: Boolean? = false) {
            if (isShowError == false) {
                binding.progressBar.visibility = View.VISIBLE
                binding.textViewError.visibility = View.GONE
                binding.buttonRetry.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.textViewError.visibility = View.VISIBLE
                binding.buttonRetry.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        protected const val TYPE_NORMAL_ITEM = 0x001
        protected const val TYPE_LOAD_MORE_ITEM = 0x002
    }
}