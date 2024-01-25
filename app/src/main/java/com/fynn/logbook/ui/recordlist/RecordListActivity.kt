package com.fynn.logbook.ui.recordlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fynn.logbook.R
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.databinding.ActivityRecordListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecordListActivity : BaseActivity<ActivityRecordListBinding>(ActivityRecordListBinding::inflate) {

    @Inject
    lateinit var viewModel: RecordListViewModel
    private var mExperimentId:Long = 0

    override fun getViewModel(): BaseViewMolder<*, *> {
        return viewModel
    }

    override fun initView() {
        val extras = intent.extras
        extras?.let {
            mExperimentId = it.getLong("")
        }
        setOperate(R.drawable.btn_add_blue){

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.sendUiIntent(RecordListIntent.GetRecordListByExperimentId(mExperimentId))
    }

    override fun initData() {

    }

    override fun initEvent() {

    }

}