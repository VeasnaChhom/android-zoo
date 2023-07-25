package com.veasnachhom.androidzoo.dataModel

import com.google.gson.annotations.SerializedName

data class AttractionPlace(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("introduction") val introduction: String,
    @SerializedName("modified") val lastModifiedData: String,
    @SerializedName("url") val url: String,
    @SerializedName("images") val images: List<Photo> = mutableListOf(),
) : BaseDiffModel {
    override fun getItemId(): String = id
}