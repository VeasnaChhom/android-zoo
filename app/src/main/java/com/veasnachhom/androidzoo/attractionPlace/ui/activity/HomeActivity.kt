package com.veasnachhom.androidzoo.attractionPlace.ui.activity

import androidx.fragment.app.Fragment
import com.veasnachhom.androidzoo.attractionPlace.ui.fragment.HomeFragment
import com.veasnachhom.androidzoo.databinding.ActivityHomeBinding
import com.veasnachhom.androidzoo.ui.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun inflateLayout(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
    override fun getDefaultFragment(): Fragment? = HomeFragment.newInstance()
}