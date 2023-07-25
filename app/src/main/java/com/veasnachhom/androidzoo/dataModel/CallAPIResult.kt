package com.veasnachhom.androidzoo.dataModel

class CallAPIResult<out T> private constructor(private val body: T?) {

    private var error: ErrorResponse? = null

    fun isSuccess() = body != null
    fun isFailure() = error != null

    internal inline fun onSuccess(result: (value: T) -> Unit) {
        if (body != null) {
            result.invoke(body)
        }
    }

    internal inline fun onFailure(result: (value: ErrorResponse) -> Unit) {
        if (error != null) {
            result.invoke(error!!)
        }
    }

    companion object {
        private fun <T> createFailure(error: ErrorResponse): CallAPIResult<T> {
            val callAPIResult = CallAPIResult<T>(null)
            callAPIResult.error = error
            return callAPIResult
        }

        fun <T> createOnSuccess(inValue: T): CallAPIResult<T> = CallAPIResult(inValue)
        fun <T> createOnFailure(error: ErrorResponse) = createFailure<T>(error)
    }
}