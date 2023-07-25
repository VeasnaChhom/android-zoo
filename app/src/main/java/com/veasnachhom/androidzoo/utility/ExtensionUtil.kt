package com.veasnachhom.androidzoo.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.veasnachhom.androidzoo.R

object ExtensionUtil {

    fun ImageView.loadImageView(url: String, isCenterCrop: Boolean? = true) {
        val builder =
            Glide.with(this).load(url).placeholder(R.drawable.ic_photo).error(R.drawable.ic_photo)
        if (isCenterCrop == true) {
            builder.centerCrop().into(this)
        } else {
            builder.into(this)
        }
    }

    fun Context.isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}