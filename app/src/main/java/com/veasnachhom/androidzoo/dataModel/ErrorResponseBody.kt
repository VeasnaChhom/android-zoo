package com.veasnachhom.androidzoo.dataModel

import com.google.gson.annotations.SerializedName

data class ErrorResponseBody(@SerializedName("error") val error: Error) {

    data class Error(
        @SerializedName("name") val name: String, @SerializedName("message") val message: String
    )
}