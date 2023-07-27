package com.veasnachhom.androidzoo.attractionPlace.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.veasnachhom.androidzoo.attractionPlace.dataModel.AttractionPlace
import com.veasnachhom.androidzoo.attractionPlace.dataModel.DisplayLanguageType
import com.veasnachhom.androidzoo.attractionPlace.repositoty.AttractionPlaceRepository
import com.veasnachhom.androidzoo.dataModel.LoadDataCallback
import com.veasnachhom.androidzoo.viewModel.BaseContentLoadingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shoeRepository: AttractionPlaceRepository
) : BaseContentLoadingViewModel() {

    var data = MutableLiveData<LoadDataCallback<AttractionPlace>>()
        private set
    var isSwiping = MutableLiveData<Boolean>()
        private set
    private var languageType: DisplayLanguageType = DisplayLanguageType.ENGLISH
    private var currentJob: Job? = null

    override fun onRetryClicked() {
        loadData()
    }

    fun loadDataOnLanguageChanged(languageType: DisplayLanguageType) {
        this.languageType = languageType
        //Cancel previous loading data job
        currentJob?.cancel()
        loadData(isSwipeToRefresh = false, isLoadMore = false)
    }

    fun loadData(
        isAppliedLanguageSelection: Boolean? = false,
        isSwipeToRefresh: Boolean? = false,
        isLoadMore: Boolean? = false
    ) {
        val isReloadData =
            (isAppliedLanguageSelection == true || (isLoadMore == false && isSwipeToRefresh == false))
        if (isReloadData) {
            showLoadingContentSkeleton()
        }

        currentJob = viewModelScope.launch {
            shoeRepository.getAttractionPlaceList(
                languageCode = languageType.code, isLoadMore = isLoadMore == true
            ).collect {
                it.onSuccess { it2 ->
                    isSwiping.postValue(false)
                    if (isReloadData && it2?.isEmpty() == true) {
                        //Empty data response
                        showError(shoeRepository.getNoDataErrorMessage())
                    } else {
                        val hasMoreData =
                            it2?.size!! >= AttractionPlaceRepository.DEFAULT_LIMIT_PER_PAGE
                        data.postValue(
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
                    isSwiping.postValue(false)
                    data.postValue(
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
                isSwiping.postValue(false)
            } else {
                isSwiping.postValue(true)
                loadData(isSwipeToRefresh = true)
            }
        }
    }
}