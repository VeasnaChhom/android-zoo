package com.veasnachhom.androidzoo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    protected lateinit var binding: B
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = inflateLayout(layoutInflater, container)
        if (isRegisterForNavigationBackButtonClickEvent() && getToolbar() != null) getToolbar()?.run {
            this.setNavigationOnClickListener {
                requireActivity().finish()
            }
        }
        return binding.root
    }

    abstract fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): B

    protected open fun getToolbar(): MaterialToolbar? {
        return null
    }

    protected open fun isRegisterForNavigationBackButtonClickEvent(): Boolean = true

    protected open fun setToolbarTitle(tile: String?) {
        getToolbar()?.run {
            title = tile
        }
    }
}