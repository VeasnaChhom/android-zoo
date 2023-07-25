package com.veasnachhom.androidzoo.dataModel

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("src") val url: String, @SerializedName("ext") val extension: String
)