package com.veasnachhom.androidzoo.attractionPlace.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.viewmodel.MutableCreationExtras
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.color.MaterialColors
import com.veasnachhom.androidzoo.R
import com.veasnachhom.androidzoo.attractionPlace.adapter.PhotoSliderAdapter
import com.veasnachhom.androidzoo.attractionPlace.dataModel.AttractionPlace
import com.veasnachhom.androidzoo.attractionPlace.ui.activity.AttractionPlaceDetailActivity
import com.veasnachhom.androidzoo.attractionPlace.ui.activity.WebViewActivity
import com.veasnachhom.androidzoo.attractionPlace.viewModel.AttractionPlaceDetailViewModel
import com.veasnachhom.androidzoo.databinding.FragmentAttractionPlaceDetailBinding
import com.veasnachhom.androidzoo.ui.fragment.BaseFragment
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class AttractionPlaceDetailFragment : BaseFragment<FragmentAttractionPlaceDetailBinding>() {

    private var attractionPlace: AttractionPlace? = null
    private val viewModel by viewModels<AttractionPlaceDetailViewModel>(extrasProducer = {
        val bundle = Bundle()
        bundle.putParcelable(AttractionPlaceDetailActivity.ATTRACTION_PLACE, attractionPlace)
        MutableCreationExtras(defaultViewModelCreationExtras).apply {
            set(DEFAULT_ARGS_KEY, bundle)
        }
    })

    override fun getToolbar(): MaterialToolbar? {
        return binding.toolbar
    }

    override fun inflateLayout(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentAttractionPlaceDetailBinding {
        return FragmentAttractionPlaceDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setToolbarTitle(attractionPlace?.name)
        bindPhotoSlider()
        binding.textviewOfficialUrl.setOnClickListener {
            startActivity(WebViewActivity.newInstance(requireContext(), attractionPlace?.url))
        }
    }

    private fun bindPhotoSlider() {
        attractionPlace?.run {
            if (images.isNotEmpty()) {
                binding.viewPager.adapter = PhotoSliderAdapter(requireActivity(), images)
                binding.indicatorView.apply {
                    setSliderColor(
                        MaterialColors.getColor(
                            binding.root,
                            com.google.android.material.R.attr.colorButtonNormal
                        ), MaterialColors.getColor(
                            binding.root,
                            com.google.android.material.R.attr.colorControlActivated
                        )
                    )
                    setSliderWidth(resources.getDimension(R.dimen.dimen_10dp))
                    setSliderHeight(resources.getDimension(R.dimen.dimen_4dp))
                    setSlideMode(IndicatorSlideMode.WORM)
                    setIndicatorStyle(IndicatorStyle.CIRCLE)
                    setupWithViewPager(binding.viewPager)
                }
            }
        }
    }

    companion object {
        fun newInstance(attractionPlace: AttractionPlace?): AttractionPlaceDetailFragment {
            val fragment = AttractionPlaceDetailFragment()
            fragment.attractionPlace = attractionPlace

            return fragment
        }
    }
}
