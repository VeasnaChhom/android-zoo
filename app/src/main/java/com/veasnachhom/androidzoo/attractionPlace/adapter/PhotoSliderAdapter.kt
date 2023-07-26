package com.veasnachhom.androidzoo.attractionPlace.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.veasnachhom.androidzoo.attractionPlace.dataModel.Photo
import com.veasnachhom.androidzoo.attractionPlace.ui.fragment.PhotoItemFragment

class PhotoSliderAdapter(
    fa: FragmentActivity, private val data: List<Photo>
) : FragmentStateAdapter(fa) {

    override fun getItemCount() = data.size

    override fun createFragment(position: Int): Fragment {
        return PhotoItemFragment.newInstance(data[position].url ?: "")
    }
}