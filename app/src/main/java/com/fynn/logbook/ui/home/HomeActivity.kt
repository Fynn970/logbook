package com.fynn.logbook.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.fynn.logbook.R
import com.fynn.logbook.adapter.HomeListAdapter
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.base.BaseAdapter
import com.fynn.logbook.base.BaseViewHolder
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState
import com.fynn.logbook.base.LoadUIState
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.databinding.ActivityHomeBinding
import com.fynn.logbook.databinding.ItemHomeBinding
import com.fynn.logbook.ui.addexperiment.AddExperimentActivity
import com.fynn.logbook.ui.recordlist.RecordListActivity
import com.fynn.logbook.util.NanoIdUtil
import com.fynn.logbook.util.observeState
import com.fynn.logbook.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(
    ActivityHomeBinding::inflate) {
    private lateinit var mAdapter: BaseAdapter<ExperimentInfo, ItemHomeBinding>
    @Inject
    lateinit var viewModel: HomeViewModel
    override fun getViewModel(): HomeViewModel {
        return viewModel
    }

    override fun initView() {
        setOperate(R.drawable.btn_add_blue) {
            intentView(AddExperimentActivity::class.java)
        }

        mAdapter = object : BaseAdapter<ExperimentInfo, ItemHomeBinding>(this, mutableListOf<ExperimentInfo>() , ItemHomeBinding::inflate){
            override fun convert(
                mContext: Context,
                holder: BaseViewHolder<ItemHomeBinding>,
                t: ExperimentInfo,
                select_position: Int
            ) {
                holder.vb.tvName.text = t.mEarTagNumber
                holder.itemView.setOnClickListener {
                    val bundel = Bundle()
                    bundel.putLong("experimentId", t.mExperimentId)
                    intentView(RecordListActivity::class.java, bundel)
                }
            }
        }


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
                mAdapter.updateData(it)
            }
        }


    }
}