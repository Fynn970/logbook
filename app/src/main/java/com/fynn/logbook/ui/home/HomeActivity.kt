package com.fynn.logbook.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fynn.logbook.R
import com.fynn.logbook.adapter.HomeListAdapter
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.base.LoadUIState
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.databinding.ActivityHomeBinding
import com.fynn.logbook.ui.addexperiment.AddExperimentActivity
import com.fynn.logbook.util.observeState
import com.fynn.logbook.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    private lateinit var mAdapter: HomeListAdapter
    @Inject
    lateinit var viewModel: HomeViewModel
    override fun initView() {
        setOperate(R.drawable.btn_add_blue) {
            intentView(AddExperimentActivity::class.java)
        }
        mAdapter = HomeListAdapter(this)
        binding.rvInfo.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = mAdapter
        }
    }


    override fun initData() {
        viewModel.sendUiIntent(HomeIntent.GetAllExperiment)
    }

    override fun initEvent() {
        viewModel.uiStateFlow.run {
            observeState(this@HomeActivity, HomeState::list) {
                mAdapter.setData(it)
            }
        }

        viewModel.loadUiIntentFlow.run {
            observeState(this@HomeActivity) {
                when (it) {
                    is LoadUIState.Loading -> {
                        if (it.isShow) {
                            showLoading()
                        } else {
                            hideLoading()
                        }
                    }

                    is LoadUIState.onError -> {
                        showToast(it.errorMsg)
                    }
                }
            }
        }
    }
}