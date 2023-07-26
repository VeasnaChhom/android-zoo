package com.veasnachhom.androidzoo.attractionPlace.dataModel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    @SerializedName("src") val url: String, @SerializedName("ext") val extension: String
) : Parcelable