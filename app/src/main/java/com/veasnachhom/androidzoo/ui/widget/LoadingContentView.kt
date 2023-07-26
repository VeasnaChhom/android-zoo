package com.veasnachhom.androidzoo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.veasnachhom.androidzoo.databinding.LayoutLoadingContentBinding
import com.veasnachhom.androidzoo.viewModel.LoadingContentViewModel

class LoadingContentView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private var viewModel: LoadingContentViewModel? = null
    private var adapter: SkeletonAdapter? = null

    private lateinit var binding: LayoutLoadingContentBinding

    init {
        context?.let {
            binding = LayoutLoadingContentBinding.inflate(
                LayoutInflater.from(context), this, true
            )
        }
    }

    fun setUpView(
        inViewModel: LoadingContentViewModel,
        @LayoutRes itemLayoutRes: Int,
        itemSize: Int,
        itemDecoration: ItemDecoration? = null
    ) {
        viewModel = inViewModel
        binding.viewModel = viewModel
        binding.lifecycleOwner = findViewTreeLifecycleOwner()
        if (adapter == null) {
            adapter = SkeletonAdapter.createAdapter(itemSize, itemLayoutRes)
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            if (itemDecoration != null) {
                binding.recyclerView.addItemDecoration(itemDecoration)
            }
            binding.recyclerView.adapter = adapter
        }
    }

    fun hideView(runnable: () -> Unit) {
        animate().alpha(0f).setDuration(500).withEndAction {
            viewModel?.hideScreenLoadingOrError()
            visibility = View.GONE
            runnable.invoke()
        }.start()
    }

    class SkeletonData

    private class SkeletonAdapter(
        private val data: List<SkeletonData>, @LayoutRes private val itemLayoutRes: Int
    ) : RecyclerView.Adapter<SkeletonAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(itemLayoutRes, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        }

        override fun getItemCount() = data.size

        private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        companion object {
            fun createAdapter(itemSize: Int, itemLayoutRes: Int): SkeletonAdapter {
                val data = arrayListOf<SkeletonData>()
                for (i in 1..itemSize) {
                    data.add(SkeletonData())
                }

                return SkeletonAdapter(data, itemLayoutRes)
            }
        }
    }
}