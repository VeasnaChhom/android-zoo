package com.veasnachhom.androidzoo.attractionPlace.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.veasnachhom.androidzoo.attractionPlace.dataModel.AttractionPlace
import com.veasnachhom.androidzoo.attractionPlace.ui.activity.AttractionPlaceDetailActivity
import com.veasnachhom.androidzoo.viewModel.BaseContentLoadingViewModel

class AttractionPlaceDetailViewModel(savedStateHandle: SavedStateHandle) :
    BaseContentLoadingViewModel() {

    val data =
        savedStateHandle.getLiveData<AttractionPlace>(AttractionPlaceDetailActivity.ATTRACTION_PLACE)

    val name = MutableLiveData<String>(data.value?.name)
    val introduction = MutableLiveData<String>(data.value?.introduction)
    val address = MutableLiveData<String>(data.value?.address)
    val lastUpdateTime = MutableLiveData<String>(data.value?.lastModifiedData)
    val siteUrl = MutableLiveData<String>(data.value?.url)
    val hasPhoto = MutableLiveData<Boolean>(data.value?.images?.isNotEmpty())
}