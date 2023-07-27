package com.veasnachhom.androidzoo.utility

import com.google.gson.Gson
import timber.log.Timber

object AppUtils {

    private val gson = Gson()

    fun logToJSONString(tag: Class<Any>, data: Any?) {
        Timber.tag(tag.simpleName).d(toJSONString(data))
    }

    fun toJSONString(data: Any?): String? {
        return if (data == null) {
            null
        } else {
            gson.toJson(data)
        }
    }
}