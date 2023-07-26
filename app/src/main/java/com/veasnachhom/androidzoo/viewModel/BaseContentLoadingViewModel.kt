package com.veasnachhom.androidzoo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseContentLoadingViewModel : ViewModel() {

    private var loadingContentViewModel = MutableLiveData<LoadingContentViewModel>()
    private var onStartShowLoadingSkeletonCallback: () -> Unit = {}

    fun bindLoadingContentViewModel(viewModel: LoadingContentViewModel) {
        loadingContentViewModel.postValue(viewModel)
        viewModel.onRetryListener {
            onRetryClicked()
        }
    }

    fun showLoadingContentSkeleton() {
        onStartShowLoadingSkeletonCallback.invoke()
        loadingContentViewModel.value?.showLoadingContentSkeleton()
    }

    fun onStartShowLoadingSkeletonCallback(callback: () -> Unit) {
        onStartShowLoadingSkeletonCallback = callback
    }

    fun showError(error: String) {
        loadingContentViewModel.value?.showError(error)
    }

    open fun onRetryClicked() {
        //Child Impl...
    }

    fun isDisplayingAnyLoadingContent(): Boolean {
        return loadingContentViewModel.value?.isDisplayingAnyContent() == true
    }
}