package com.veasnachhom.androidzoo.attractionPlace.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.veasnachhom.androidzoo.attractionPlace.dataModel.AttractionPlace
import com.veasnachhom.androidzoo.attractionPlace.dataModel.DisplayLanguageType
import com.veasnachhom.androidzoo.dataModel.ErrorResponse
import com.veasnachhom.androidzoo.attractionPlace.repositoty.AttractionPlaceRepository
import com.veasnachhom.androidzoo.viewModel.BaseContentLoadingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shoeRepository: AttractionPlaceRepository
) : BaseContentLoadingViewModel() {

    private var _data = MutableLiveData<LoadDataCallback>()
    val data = _data
    private var _isSwiping = MutableLiveData<Boolean>()
    val isSwiping = _isSwiping

    override fun onRetryClicked() {
        loadData()
    }

    fun loadData(
        isSwipeToRefresh: Boolean? = false, isLoadMore: Boolean? = false
    ) {
        val previousDataEmpty = ((isLoadMore == false && isSwipeToRefresh == false))
        if (previousDataEmpty) {
            showLoadingContentSkeleton()
        }
        viewModelScope.launch {
            shoeRepository.getAttractionPlaceList(
                languageCode = DisplayLanguageType.ENGLISH.code, isLoadMore = isLoadMore == true
            ).collect() {
                it.onSuccess { it2 ->
                    _isSwiping.postValue(false)
                    if (previousDataEmpty && it2?.isEmpty() == true) {
                        //Empty data response
                        showError(shoeRepository.getNoDataErrorMessage())
                    } else {
                        val hasMoreData =
                            it2?.size!! >= AttractionPlaceRepository.DEFAULT_LIMIT_PER_PAGE
                        _data.postValue(
                            LoadDataCallback(
                                data = it2,
                                isLoadMore = isLoadMore,
                                hasMoreData = hasMoreData,
                                isSwipeToRefresh = isSwipeToRefresh,
                            )
                        )
                    }
                }
                it.onFailure { it2 ->
                    if (isLoadMore == false && isSwipeToRefresh == false) {
                        if (it2.message != null) {
                            showError(it2.message)
                        }
                    }
                    _isSwiping.postValue(false)
                    _data.postValue(
                        LoadDataCallback(
                            error = it2,
                            isLoadMore = isLoadMore,
                            isSwipeToRefresh = isSwipeToRefresh,
                        )
                    )
                }
            }
        }
    }

    fun onSwipeToRefresh(): SwipeRefreshLayout.OnRefreshListener {
        return SwipeRefreshLayout.OnRefreshListener {
            //Ignore swipe to refresh action
            if (isDisplayingAnyLoadingContent()) {
                _isSwiping.postValue(false)
            } else {
                _isSwiping.postValue(true)
                loadData(isSwipeToRefresh = true)
            }
        }
    }

    data class LoadDataCallback(
        val data: List<AttractionPlace>? = arrayListOf(),
        val error: ErrorResponse? = null,
        val isSwipeToRefresh: Boolean? = false,
        val isLoadMore: Boolean? = false,
        val hasMoreData: Boolean? = false
    )
}