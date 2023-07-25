package com.veasnachhom.androidzoo.retrofit

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.veasnachhom.androidzoo.dataModel.CallAPIResult
import com.veasnachhom.androidzoo.dataModel.ErrorResponse
import com.veasnachhom.androidzoo.dataModel.ErrorResponseBody
import com.veasnachhom.androidzoo.utility.ExtensionUtil.isInternetAvailable
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException

object ResponseResultParser {

    fun <T> parseResultFromSuccess(context: Context, result: Response<T>): CallAPIResult<T?> {
        return try {
            if (result.isSuccessful) {
                CallAPIResult.createOnSuccess(result.body())
            } else {
                val parseErrorResponse = result.parseErrorResponse()
                if (parseErrorResponse?.error != null) {
                    CallAPIResult.createOnFailure(
                        ErrorResponse(
                            result.code().toString(), parseErrorResponse.error.message
                        )
                    )
                } else {
                    CallAPIResult.createOnFailure(
                        ErrorResponse(
                            result.code().toString(), result.message()
                        )
                    )
                }
            }
        } catch (ex: Exception) {
            Timber.e(ex)
            if (ex is ConnectException) {
                return if (!context.isInternetAvailable()) {
                    CallAPIResult.createOnFailure(ErrorResponse.noInternetError(context))
                } else {
                    CallAPIResult.createOnFailure(ErrorResponse.connectSererError(context))
                }
            } else {
                CallAPIResult.createOnFailure(
                    ErrorResponse.generalErrorWithMessage(
                        context, ex.message
                    )
                )
            }
        }
    }

    fun <T> parseResultFromException(context: Context, ex: Exception): CallAPIResult<T?> {
        return if (ex is ConnectException) {
            if (!context.isInternetAvailable()) {
                CallAPIResult.createOnFailure(ErrorResponse.noInternetError(context))
            } else {
                CallAPIResult.createOnFailure(ErrorResponse.connectSererError(context))
            }
        } else {
            CallAPIResult.createOnFailure(
                ErrorResponse.generalErrorWithMessage(
                    context, ex.message
                )
            )
        }
    }

    private fun <T> Response<T>.parseErrorResponse(): ErrorResponseBody? {
        val errorBody = errorBody()
        if (errorBody != null) {
            val gson = Gson()
            val type = object : TypeToken<ErrorResponseBody>() {}.type
            return gson.fromJson(errorBody.charStream(), type)
        }

        return null
    }
}