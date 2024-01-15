package com.fynn.logbook.ui.addexperiment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.fynn.logbook.R
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.databinding.ActivityAddExperimentBinding
import com.fynn.logbook.util.observeState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddExperimentActivity : BaseActivity<ActivityAddExperimentBinding>(ActivityAddExperimentBinding::inflate) {

    private var info: ExperimentInfo? = null
    @Inject
    lateinit var viewModel: AddExperimentViewModel
    override fun initView() {
        setOperate(R.drawable.ic_save){
            if (!TextUtils.isEmpty(binding.etName.text.toString())){
                info = ExperimentInfo(mName = binding.etName.text.toString())
                viewModel.sendUiIntent(AddExperimentIntent.SaveExperiment(info!!))
            }
        }

    }

    override fun initData() {

    }

    override fun initEvent() {
        viewModel.uiStateFlow.run {
            observeState(this@AddExperimentActivity, AddExperimentState::isFinishView){
                if (it){
                    finish()
                }
            }
        }
    }

}