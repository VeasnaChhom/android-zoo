package com.veasnachhom.androidzoo.attractionPlace.ui.activity

import android.os.Bundle
import com.veasnachhom.androidzoo.R
import com.veasnachhom.androidzoo.ui.activity.BaseActivity
import com.veasnachhom.androidzoo.attractionPlace.ui.fragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        replaceFragment(HomeFragment.newInstance())
    }
}