package com.veasnachhom.androidzoo.attractionPlace.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.veasnachhom.androidzoo.databinding.FragmentPhotoItemBinding
import com.veasnachhom.androidzoo.ui.fragment.BaseFragment
import com.veasnachhom.androidzoo.utility.ExtensionUtil.loadImageView

class PhotoItemFragment : BaseFragment<FragmentPhotoItemBinding>() {

    private var photoUrl: String? = null

    override fun inflateLayout(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentPhotoItemBinding = FragmentPhotoItemBinding.inflate(layoutInflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoUrl?.let {
            binding.imageview.loadImageView(it, false)
        }
    }

    companion object {

        fun newInstance(photoUrl: String): PhotoItemFragment {
            val fragment = PhotoItemFragment()
            fragment.photoUrl = photoUrl

            return fragment
        }
    }
}