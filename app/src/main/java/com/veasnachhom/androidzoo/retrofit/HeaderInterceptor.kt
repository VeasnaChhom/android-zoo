package com.veasnachhom.androidzoo.retrofit

import okhttp3.*
import okhttp3.Interceptor.*
import java.io.IOException

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        var request = chain.request()
        request = request.newBuilder().addHeader("Accept", "application/json;charset=utf-8").build()
        return chain.proceed(request)
    }
}
