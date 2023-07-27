package com.veasnachhom.androidzoo.retrofit

import com.veasnachhom.androidzoo.BuildConfig
import com.veasnachhom.androidzoo.attractionPlace.api.AttractionPlaceAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitManager {

    private const val BASE_URL = "https://www.travel.taipei/open-api/"

    @Provides
    @Singleton
    fun provideRetrofit(
        loggingInterceptor: HttpLoggingInterceptor, headerInterceptor: HeaderInterceptor
    ): Retrofit {
        val clientBuilder = OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)

        clientBuilder.addInterceptor(loggingInterceptor)
        clientBuilder.addInterceptor(headerInterceptor)
        val builder: Retrofit.Builder =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideAttractionPlaceAPI(retrofitClient: Retrofit): AttractionPlaceAPI {
        return retrofitClient.create(AttractionPlaceAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): HeaderInterceptor = HeaderInterceptor()
}