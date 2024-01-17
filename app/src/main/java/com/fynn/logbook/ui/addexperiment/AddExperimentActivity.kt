package com.fynn.logbook.ui.addexperiment

import android.text.TextUtils
import com.fynn.logbook.R
import com.fynn.logbook.base.BaseActivity
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.databinding.ActivityAddExperimentBinding
import com.fynn.logbook.util.observeState
import com.fynn.logbook.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddExperimentActivity :
    BaseActivity<ActivityAddExperimentBinding, AddExperimentViewModel>(ActivityAddExperimentBinding::inflate) {

    private var info: ExperimentInfo? = null

    @Inject
    lateinit var viewModel: AddExperimentViewModel
    override fun getViewModel(): AddExperimentViewModel {
        return viewModel
    }

    override fun initView() {
        setOperate(R.drawable.ic_save) {
            val earTagNumber = binding.etEarTagNumber.text.toString()
            val projectName = binding.tvProject.text.toString()
            val timeInterval = binding.etInterval.text.toString()
            val animalMerchant = binding.etAnimalMerchants.text.toString()
            if (TextUtils.isEmpty(earTagNumber)) {
                showToast("请输入耳标号")
                return@setOperate
            }
            if (TextUtils.isEmpty(projectName)){
                showToast("请选择项目")
                return@setOperate
            }
            if (TextUtils.isEmpty(timeInterval)){
                showToast("请输入间隔天数")
                return@setOperate
            }
            if (TextUtils.isEmpty(animalMerchant)){
                showToast("请输入动物厂商")
                return@setOperate
            }
            viewModel.sendUiIntent(AddExperimentIntent.SaveExperiment(earTagNumber, projectName,animalMerchant, timeInterval, if (binding.swState.isChecked) 0 else 1))

        }

    }

    override fun initData() {

    }

    override fun initEvent() {
        viewModel.uiStateFlow.run {
            observeState(this@AddExperimentActivity, AddExperimentState::isFinishView) {
                if (it) {
                    finish()
                }
            }
        }
    }

}