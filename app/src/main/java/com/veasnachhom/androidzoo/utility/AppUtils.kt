package com.veasnachhom.androidzoo.utility

import com.google.gson.Gson
import timber.log.Timber

object AppUtils {

    fun logToJSONString(tag: Class<Any>, data: Any?) {
        Timber.tag(tag.simpleName).d(Gson().toJson(data))
    }
}