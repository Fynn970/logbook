package com.fynn.logbook.ui.addrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.fynn.logbook.R
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.databinding.ActivityAddRecordBinding
import com.fynn.logbook.ui.recordlist.RecordListIntent
import com.fynn.logbook.ui.recordlist.RecordListState
import com.fynn.logbook.ui.recordlist.RecordListViewModel
import com.fynn.logbook.util.observeState
import com.fynn.logbook.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddRecordActivity : BaseActivity<ActivityAddRecordBinding>(ActivityAddRecordBinding::inflate) {

    private var mExperimentId: Long = 0

    @Inject
    lateinit var viewModel: RecordListViewModel

    override fun getViewModel(): BaseViewMolder<*, *> {
        return viewModel
    }

    override fun initData() {
        val extras = intent.extras
        extras?.let {
            mExperimentId = it.getLong("experimentId")
        }
        setOperate(R.drawable.ic_save){
            if (TextUtils.isEmpty(binding.etRecordNum.text.toString())){
                showToast("请输入编号")
                return@setOperate
            }
            if (TextUtils.isEmpty(binding.etRoomNum.text.toString())){
                showToast("请输入房间号")
                return@setOperate
            }

            if (TextUtils.isEmpty(binding.etAnimalWeight.text.toString())){
                showToast("请输入体重")
                return@setOperate
            }
            viewModel.sendUiIntent(RecordListIntent.SaveRecordInfo(mExperimentId, binding.etRecordNum.text.toString(),
                binding.etRoomNum.text.toString(),binding.etAnimalWeight.text.toString()))

        }
    }

    override fun initEvent() {
        viewModel.uiStateFlow.run {
            observeState(this@AddRecordActivity, RecordListState::isFinishView){
                if (it){
                    finish()
                }
            }
        }
    }

}