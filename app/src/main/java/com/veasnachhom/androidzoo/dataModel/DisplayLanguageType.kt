package com.veasnachhom.androidzoo.dataModel

import android.content.Context
import androidx.annotation.StringRes
import com.veasnachhom.androidzoo.R

enum class DisplayLanguageType(val code: String, @StringRes private val displayNameRes: Int) {

    ENGLISH("en", R.string.english), TAIPEI("zh-tw", R.string.taiwan), CHINESE(
        "zh-cn", R.string.chinese
    ),
    JAPANESE("ja", R.string.japanese), KOREAN("ko", R.string.korean), SPANISH(
        "es", R.string.spanish
    ),
    INDONESIAN("id", R.string.indonesian), THAI("th", R.string.thai), VIETNAMESE(
        "vi", R.string.vietnamese
    );

    fun getDisplayName(context: Context): String = context.getString(displayNameRes)
}