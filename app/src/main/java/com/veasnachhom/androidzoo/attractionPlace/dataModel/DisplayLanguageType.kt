package com.veasnachhom.androidzoo.attractionPlace.dataModel

import androidx.annotation.IdRes
import com.veasnachhom.androidzoo.R

enum class DisplayLanguageType(
    val code: String, @IdRes val menuId: Int
) {

    ENGLISH("en", R.id.menu_en), TAIPEI(
        "zh-tw", R.id.menu_zh_tw
    ),
    CHINESE(
        "zh-cn", R.id.menu_zh_cn
    ),
    JAPANESE(
        "ja", R.id.menu_ja
    ),
    KOREAN(
        "ko", R.id.menu_ko
    ),
    SPANISH(
        "es", R.id.menu_es
    ),
    INDONESIAN(
        "id", R.id.menu_id
    ),
    THAI("th", R.id.menu_th),

    VIETNAMESE(
        "vi", R.id.menu_vi
    );

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