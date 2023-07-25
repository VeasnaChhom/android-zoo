package com.veasnachhom.androidzoo.dataModel

import android.content.Context
import com.veasnachhom.androidzoo.R

data class ErrorResponse(val code: String? = "", val message: String? = "") {

    companion object {
        private const val ERROR_NO_NETWORK_CONNECTION = "NETWORK"
        private const val ERROR_NETWORK_CONNECTION = "CONNECTION"
        private const val ERROR_GENERAL = "GENERAL"

        fun noInternetError(context: Context) = ErrorResponse(
            ERROR_NO_NETWORK_CONNECTION, context.getString(R.string.error_no_internet)
        )

        fun connectSererError(context: Context) = ErrorResponse(
            ERROR_NETWORK_CONNECTION, context.getString(R.string.error_connect_server)
        )

        fun generalErrorWithMessage(
            context: Context, message: String? = context.getString(R.string.error_general)
        ) = ErrorResponse(
            ERROR_GENERAL, message
        )
    }
}
