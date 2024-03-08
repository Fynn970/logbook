package com.fynn.logbook.ui.addexperiment

import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.repository.IAppRepostory
import com.fynn.logbook.util.showToast

class AddExperimentViewModel(private val repository: IAppRepostory) :
    BaseViewMolder<AddExperimentState, AddExperimentIntent>() {
    override fun initUiState(): AddExperimentState {
        return AddExperimentState()
    }

    override fun handleIntent(intent: IUiEvent) {
        when (intent) {
            is AddExperimentIntent.SaveExperiment -> {
                saveExperimentInfo(
                    intent.earTagNumber,
                    intent.animalMerchant,
                    intent.timeInterval,
                    intent.isAlive
                )
            }
        }
    }

    private fun saveExperimentInfo(
        earTagNumber: String,
        animalMerchant: String,
        timeInterval: String,
        alive: Int
    ) {
        scope {
            val saveInfo = ExperimentInfo(
                mEarTagNumber = earTagNumber,
                mAnimalState = alive,
                mDayInterval = timeInterval.toInt(),
                animalMerchants = animalMerchant
            )
            val saveExperiment = repository.saveExperiment(saveInfo)
            if (saveExperiment) {
                sendUiState {
                    copy(isFinishView = true)
                }
                showToast("保存成功")
            } else {
                showToast("保存失败")
            }
        }
    }
}

data class AddExperimentState(
    val isFinishView: Boolean = false,
) : IUiState


sealed class AddExperimentIntent : IUiEvent {
    data class SaveExperiment(
        val earTagNumber: String,
//        val projectName: String,
        val animalMerchant: String,
        val timeInterval: String,
        val isAlive: Int
    ) : AddExperimentIntent()
}
