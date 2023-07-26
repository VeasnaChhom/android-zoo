package com.veasnachhom.androidzoo.attractionPlace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.veasnachhom.androidzoo.R
import com.veasnachhom.androidzoo.adapter.BaseLoadMoreAdapter
import com.veasnachhom.androidzoo.attractionPlace.dataModel.AttractionPlace
import com.veasnachhom.androidzoo.databinding.ListItemAttractionPlaceBinding
import com.veasnachhom.androidzoo.utility.ExtensionUtil.loadImageView

class AttractionPlaceAdapter(data: ArrayList<AttractionPlace> = arrayListOf()) :
    BaseLoadMoreAdapter<AttractionPlace>(data) {

    private var onItemClickCallback: ((Int, AttractionPlace) -> Unit)? = null

    override fun onCreateChildViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        val itemBinding = ListItemAttractionPlaceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ShoeViewHolder(itemBinding)
    }

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShoeViewHolder) {
            holder.bindData(data[position], onItemClickCallback)
        }
    }

    fun onItemClickCallback(callback: (Int, AttractionPlace) -> Unit) {
        this.onItemClickCallback = callback
    }

    class ShoeViewHolder(private val binding: ListItemAttractionPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(place: AttractionPlace, onItemClick: ((Int, AttractionPlace) -> Unit)?) {
            if (place.images.isNotEmpty()) {
                place.images[0].let {
                    binding.imageLogo.loadImageView(it.url)
                }
            } else {
                binding.imageLogo.setImageResource(R.drawable.ic_photo)
            }
            binding.textviewName.text = place.name
            binding.textviewIntroduction.text = place.introduction
            binding.root.setOnClickListener {
                onItemClick?.let {
                    onItemClick.invoke(adapterPosition, place)
                }
            }
        }
    }
}