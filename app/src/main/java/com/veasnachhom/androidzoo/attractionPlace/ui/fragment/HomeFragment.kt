package com.veasnachhom.androidzoo.attractionPlace.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.veasnachhom.androidzoo.R
import com.veasnachhom.androidzoo.attractionPlace.adapter.AttractionPlaceAdapter
import com.veasnachhom.androidzoo.attractionPlace.dataModel.AttractionPlace
import com.veasnachhom.androidzoo.attractionPlace.dataModel.DisplayLanguageType
import com.veasnachhom.androidzoo.attractionPlace.ui.activity.AttractionPlaceDetailActivity
import com.veasnachhom.androidzoo.attractionPlace.viewModel.HomeViewModel
import com.veasnachhom.androidzoo.dataModel.LoadDataCallback
import com.veasnachhom.androidzoo.databinding.FragmentHomeBinding
import com.veasnachhom.androidzoo.ui.decorator.DefaultItemDecoration
import com.veasnachhom.androidzoo.ui.fragment.BaseFragment
import com.veasnachhom.androidzoo.ui.layoutmanager.LinearLoadMoreLayoutManager
import com.veasnachhom.androidzoo.viewModel.LoadingContentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()
    private val loadingContentViewModel by viewModels<LoadingContentViewModel>()
    private var layoutManager: LinearLoadMoreLayoutManager? = null
    private var adapter: AttractionPlaceAdapter? = null
    private lateinit var activityAttractionPlaceDetailResultLauncher: ActivityResultLauncher<Intent>

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
            bindData(it)
        }
        binding.root.post {
            binding.loadingContentSkeleton.setUpView(
                loadingContentViewModel,
                R.layout.list_item_skeleton_attraction_place,
                8,
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
            binding.recyclerview.visibility = View.GONE
        }
        activityAttractionPlaceDetailResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {}
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_lang -> {
                    showDisplayLanguagePopupMenu(
                        binding.toolbar.findViewById(menuItem.itemId), R.menu.menu_display_language
                    )
                    true
                }

                else -> false
            }
        }
        viewModel.loadData()
    }

    private fun showDisplayLanguagePopupMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            val selectedLanguage = DisplayLanguageType.fromMenuId(menuItem.itemId)
            viewModel.loadDataOnLanguageChanged(selectedLanguage)
            true
        }
        popup.show()
    }

    private fun bindData(data: LoadDataCallback<AttractionPlace>) {
        if (data.error == null) {
            if (adapter == null) {
                adapter = AttractionPlaceAdapter(data.data as ArrayList<AttractionPlace>)
                adapter?.canLoadMore = data.hasMoreData == true
                layoutManager = LinearLoadMoreLayoutManager(requireContext())
                layoutManager?.onReachedLoadMoreBottom {
                    viewModel.loadData(isLoadMore = true)
                }
                layoutManager?.onReachedLoadMoreByDefault {
                    viewModel.loadData(isLoadMore = true)
                }
                layoutManager?.setShouldDetectLoadMore(data.hasMoreData)
                binding.recyclerview.layoutManager = layoutManager
                if (binding.recyclerview.itemDecorationCount == 0) {
                    binding.recyclerview.addItemDecoration(
                        DefaultItemDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.dimen_16dp
                            ), resources.getDimensionPixelSize(
                                R.dimen.dimen_8dp
                            )
                        )
                    )
                }
                binding.recyclerview.adapter = adapter
                adapter?.onRetryLoadMore {
                    viewModel.loadData(isLoadMore = true)
                }
                adapter?.onItemClickCallback { _, attractionPlace ->
                    activityAttractionPlaceDetailResultLauncher.launch(
                        AttractionPlaceDetailActivity.newInstance(requireContext(), attractionPlace)
                    )
                }
                hideLoadingContentSkeleton()
            } else {
                layoutManager?.setShouldDetectLoadMore(data.hasMoreData)
                if (data.isSwipeToRefresh == true) {
                    adapter?.setRefreshData(data.data as ArrayList, data.hasMoreData == true)
                } else if (data.isLoadMore == true) {
                    if (data.data?.isEmpty() == true) {
                        adapter?.removeLoadMoreLayout()
                    }
                    adapter?.addLoadMoreItem(data.data as ArrayList, data.hasMoreData == true)
                    layoutManager?.loadingFinished()
                } else {
                    //Reset data
                    adapter?.resetData(data.data as ArrayList, data.hasMoreData == true)
                    hideLoadingContentSkeleton()
                }
            }
        } else {
            if (data.isLoadMore == true) {
                adapter?.onLoadMoreDataError()
            }
        }
    }

    private fun hideLoadingContentSkeleton() {
        binding.loadingContentSkeleton.hideView {
            binding.recyclerview.visibility = View.VISIBLE
            binding.recyclerview.animate().alpha(1f).setDuration(100).start()
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}