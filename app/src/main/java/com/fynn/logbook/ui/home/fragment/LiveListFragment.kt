package com.fynn.logbook.ui.home.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fynn.logbook.base.BaseAdapter
import com.fynn.logbook.base.BaseFragment
import com.fynn.logbook.base.BaseViewHolder
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.databinding.FragmentLiveListBinding
import com.fynn.logbook.databinding.ItemHomeBinding
import com.fynn.logbook.ui.home.HomeIntent
import com.fynn.logbook.ui.home.HomeState
import com.fynn.logbook.ui.recordlist.RecordListActivity
import com.fynn.logbook.util.DateUtils
import com.fynn.logbook.util.observeState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LiveListFragment :
    BaseFragment<FragmentLiveListBinding>(FragmentLiveListBinding::inflate) {

    @Inject
    lateinit var viewModel: LiveListViewModel

    private lateinit var mAdapter: BaseAdapter<ExperimentInfo, ItemHomeBinding>
    private var mViewType:Int = 1
    override fun getViewModel(): BaseViewMolder<*, *>? {
        return viewModel
    }


    override fun initView() {
        mAdapter = object : BaseAdapter<ExperimentInfo, ItemHomeBinding>(
            requireContext(),
            mutableListOf(),
            ItemHomeBinding::inflate
        ) {
            override fun convert(
                mContext: Context,
                holder: BaseViewHolder<ItemHomeBinding>,
                t: ExperimentInfo,
                select_position: Int
            ) {
                holder.vb.let {
                    it.tvEarTagNum.text = t.mEarTagNumber
                    it.tvProcessName.text = t.mProcessName
                    it.tvAnimalMerchants.text = t.animalMerchants
                    it.tvIntervalSet.text = "${t.mDayInterval}天"
                    it.tvIntervalState.text = if (DateUtils.campareLongDate(t.mUpdateDate, t.mDayInterval)) "达标" else "不达标"
                    it.tvAnimalState.text = if (t.mAnimalState == 1)"存活" else "死亡"
                }
                holder.itemView.setOnClickListener {
                    val bundel = Bundle()
                    bundel.putLong("experimentId", t.mExperimentId)
                    intentView(RecordListActivity::class.java, bundel)
                }
            }
        }
        binding.refresh.setOnRefreshListener {
            viewModel.sendUiIntent(LiveListUiEvent.GetAllLiveExperiment(mViewType))
        }

        binding.rvInfo.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = mAdapter
        }
    }

    override fun initData() {

        viewModel.sendUiIntent(LiveListUiEvent.GetAllLiveExperiment(mViewType))
    }


    override fun initEvent() {
        viewModel.uiStateFlow.run {
            observeState(viewLifecycleOwner, LiveListUiState::list) {
                mAdapter.updateData(it)
            }
        }


    }

    fun setViewType(viewType: Int){
        mViewType = viewType
    }

    override fun refreshEnd() {
        binding.refresh.isRefreshing = false
    }

}