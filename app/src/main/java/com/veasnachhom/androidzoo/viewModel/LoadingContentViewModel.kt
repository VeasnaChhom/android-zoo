package com.veasnachhom.androidzoo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoadingContentViewModel : ViewModel() {

    private var errorMessage = MutableLiveData<String>()
    private var displayError = MutableLiveData<Boolean>()
    private var displayLoadingContentSkeleton = MutableLiveData(true)
    private var onRetryListener: () -> Unit = {}

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }

    fun getDisplayError(): LiveData<Boolean> {
        return displayError
    }

    fun getDisplayLoadingContentSkeleton(): LiveData<Boolean> {
        return displayLoadingContentSkeleton
    }

    fun onRetryClicked() {
        onRetryListener.invoke()
    }

    fun onRetryListener(listener: () -> Unit) {
        onRetryListener = listener
    }

    fun showError(error: String) {
        displayError.postValue(true)
        displayLoadingContentSkeleton.postValue(false)
        errorMessage.postValue(error)
    }

    fun showLoadingContentSkeleton() {
        displayLoadingContentSkeleton.postValue(true)
        displayError.postValue(false)
    }

    fun hideScreenLoadingOrError() {
        displayError.postValue(false)
        errorMessage.postValue("")
        displayLoadingContentSkeleton.postValue(false)
    }

    fun isDisplayingAnyContent(): Boolean {
        return displayError.value == true || displayLoadingContentSkeleton.value == true
    }
}