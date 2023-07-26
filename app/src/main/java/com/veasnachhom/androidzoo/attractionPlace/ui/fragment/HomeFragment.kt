package com.veasnachhom.androidzoo.attractionPlace.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.veasnachhom.androidzoo.R
import com.veasnachhom.androidzoo.databinding.FragmentHomeBinding
import com.veasnachhom.androidzoo.ui.decorator.DefaultItemDecoration
import com.veasnachhom.androidzoo.ui.fragment.BaseFragment
import com.veasnachhom.androidzoo.ui.layoutmanager.LinearLoadMoreLayoutManager
import com.veasnachhom.androidzoo.attractionPlace.viewModel.HomeViewModel
import com.veasnachhom.androidzoo.viewModel.LoadingContentViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()
    private val loadingContentViewModel by viewModels<LoadingContentViewModel>()
    private var layoutManager: LinearLoadMoreLayoutManager? = null

    override fun inflateLayout(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bindLoadingContentViewModel(loadingContentViewModel)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.data.observe(viewLifecycleOwner) {
            Timber.d("Data = {${Gson().toJson(it)}}")
        }
        binding.root.post {
            binding.loadingContentSkeleton.setUpView(
                loadingContentViewModel,
                R.layout.list_item_skeleton_attraction_place,
                10,
                DefaultItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.dimen_16dp
                    ), resources.getDimensionPixelSize(
                        R.dimen.dimen_8dp
                    )
                )
            )
        }
        viewModel.onStartShowLoadingSkeletonCallback {
            binding.loadingContentSkeleton.visibility = View.VISIBLE
            binding.loadingContentSkeleton.alpha = 1f
            binding.recyclerView.visibility = View.GONE
        }
        viewModel.loadData()
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}