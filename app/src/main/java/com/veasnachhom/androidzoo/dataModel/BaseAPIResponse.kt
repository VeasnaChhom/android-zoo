package com.veasnachhom.androidzoo.dataModel

import com.google.gson.annotations.SerializedName

data class BaseAPIResponse<D>(
    @SerializedName("total") val total: Int, @SerializedName("data") val data: D
)