package com.veasnachhom.androidzoo.attractionPlace.dataModel

import android.content.Context
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.veasnachhom.androidzoo.R

enum class DisplayLanguageType(
    val code: String, @IdRes val menuId: Int, @StringRes private val displayNameRes: Int
) {

    ENGLISH("en", R.id.menu_en, R.string.english), TAIPEI(
        "zh-tw", R.id.menu_zh_tw, R.string.taiwan
    ),
    CHINESE(
        "zh-cn", R.id.menu_zh_cn, R.string.chinese
    ),
    JAPANESE(
        "ja", R.id.menu_ja, R.string.japanese
    ),
    KOREAN(
        "ko", R.id.menu_ko, R.string.korean
    ),
    SPANISH(
        "es", R.id.menu_es, R.string.spanish
    ),
    INDONESIAN(
        "id", R.id.menu_id, R.string.indonesian
    ),
    THAI("th", R.id.menu_th, R.string.thai),

    VIETNAMESE(
        "vi", R.id.menu_vi, R.string.vietnamese
    );

    fun getDisplayName(context: Context): String = context.getString(displayNameRes)

    companion object {

        fun fromMenuId(@IdRes menuId: Int): DisplayLanguageType {
            DisplayLanguageType.values().forEach {
                if (it.menuId == menuId) {
                    return it
                }
            }

            return ENGLISH
        }
    }
}