package com.veasnachhom.androidzoo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B> : Fragment() where B : ViewDataBinding {

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = inflateLayout(layoutInflater, container)
        return binding.root
    }

    abstract fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): B
}