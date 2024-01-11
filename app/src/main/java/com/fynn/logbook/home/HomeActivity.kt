package com.fynn.logbook.home

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fynn.logbook.R
import com.fynn.logbook.adapter.HomeListAdapter
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.databinding.ActivityHomeBinding
import com.fynn.logbook.util.observeState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    private lateinit var mAdapter: HomeListAdapter
    @Inject lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = HomeListAdapter(this)
        binding.rvInfo.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = mAdapter
        }
        initClick()
    }

    private fun initClick() {
        binding.rlAddExperiment.setOnClickListener {
            val info = ExperimentInfo(mName = "daosdaosd")
            viewModel.sendUiIntent(HomeIntent.SaveExperiment(info))
        }
    }

    override fun initData() {
        viewModel.sendUiIntent(HomeIntent.GetAllExperiment)
    }

    override fun initEvent() {
        viewModel.uiStateFlow.run {
            observeState(this@HomeActivity, HomeState::list){
                mAdapter.setData(it)
            }
        }
    }
}