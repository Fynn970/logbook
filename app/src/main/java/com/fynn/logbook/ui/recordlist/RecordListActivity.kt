package com.fynn.logbook.ui.recordlist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fynn.logbook.R
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.base.BaseAdapter
import com.fynn.logbook.base.BaseViewHolder
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.bean.RecordInfo
import com.fynn.logbook.databinding.ActivityRecordListBinding
import com.fynn.logbook.databinding.ItemRecordInfoBinding
import com.fynn.logbook.ui.addrecord.AddRecordActivity
import com.fynn.logbook.ui.home.fragment.LiveListUiState
import com.fynn.logbook.util.DateUtils
import com.fynn.logbook.util.observeState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecordListActivity : BaseActivity<ActivityRecordListBinding>(ActivityRecordListBinding::inflate) {

    @Inject
    lateinit var viewModel: RecordListViewModel

    private var mExperimentId:Long = -1
    private lateinit var mAdapter : BaseAdapter<RecordInfo, ItemRecordInfoBinding>
    override fun getViewModel(): BaseViewMolder<*, *> {
        return viewModel
    }

    override fun initView() {
        val extras = intent.extras
        extras?.let {
            mExperimentId = it.getLong("experimentId")
        }
        mAdapter = object: BaseAdapter<RecordInfo, ItemRecordInfoBinding>(this, mutableListOf(), ItemRecordInfoBinding::inflate){
            override fun convert(
                mContext: Context,
                holder: BaseViewHolder<ItemRecordInfoBinding>,
                t: RecordInfo,
                select_position: Int
            ) {
                holder.vb.tvRecordId.text = t.mRecordNum
                holder.vb.tvRoomName.text = t.mRoomNumber
                holder.vb.tvAnimalWeight.text = "${t.mRecordWeight}"
                holder.vb.tvCreateDate.text = DateUtils.longToString(t.mRecordCreateDate)
                holder.vb.tvUpdateDate.text = DateUtils.longToString(t.mRecordUpdateDate)
            }
        }

        setOperate(R.drawable.btn_add_blue){
            val bundle = Bundle()
            bundle.putLong("experimentId", mExperimentId)
            intentView(AddRecordActivity::class.java, bundle)
        }
    }

    override fun onResume() {
        super.onResume()
        if (mExperimentId != -1L){
            viewModel.sendUiIntent(RecordListIntent.GetRecordListByExperimentId(mExperimentId))
        }
    }

    override fun initData() {

    }

    override fun initEvent() {
        viewModel.uiStateFlow.run {
            observeState(this@RecordListActivity, RecordListState::recordList) {
                mAdapter.updateData(it)
            }
        }
    }

}