package com.veasnachhom.androidzoo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.veasnachhom.androidzoo.R

open class BaseActivity : AppCompatActivity() {

    protected fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(getRootContainerId(), fragment, fragment.javaClass.simpleName).commit()
    }

    protected fun getRootContainerId(): Int {
        return R.id.root_view
    }
}