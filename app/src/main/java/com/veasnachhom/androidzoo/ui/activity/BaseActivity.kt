package com.veasnachhom.androidzoo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.veasnachhom.androidzoo.R

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout()
        setContentView(binding.root)
        navigateToDefaultFragment()
    }

    protected abstract fun inflateLayout(): B

    protected fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(getRootContainerId(), fragment, fragment.javaClass.simpleName).commit()
    }

    protected fun getRootContainerId(): Int {
        return R.id.root_view
    }

    private fun navigateToDefaultFragment() {
        getDefaultFragment()?.let {
            replaceFragment(it)
        }
    }

    protected abstract fun getDefaultFragment(): Fragment?
}