package com.fynn.logbook.ui.home

import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.repository.IAppRepostory
import com.fynn.logbook.util.showToast

class HomeViewModel constructor(private val repository: IAppRepostory) :
    BaseViewMolder<HomeState, HomeIntent>() {


    override fun initUiState(): HomeState {
        return HomeState()
    }

    override fun handleIntent(intent: IUiEvent) {
        when (intent) {
            is HomeIntent.GetAllExperiment -> {
                getAllExperiment()
            }
            is HomeIntent.SaveExperiment -> {
                saveExperimentInfo(intent.info)
            }
        }
    }

    private fun saveExperimentInfo(info:ExperimentInfo){
        scope {
            val saveExperiment = repository.saveExperiment(info)
            if (saveExperiment){
                val list: MutableList<ExperimentInfo> = uiStateFlow.value.list.toMutableList()
                list.add(info)
                sendUiState {
                    copy(list = list)
                }
            }else{
                showToast("保存失败")
            }
        }
    }

    private fun getAllExperiment() {
        scope {
            val list = repository.getExperimentList()
            sendUiState {
                copy(list = list)
            }
        }
    }
}


data class HomeState(
    val list: List<ExperimentInfo> = emptyList(),
) : IUiState

sealed class HomeIntent : IUiEvent {
    object GetAllExperiment: HomeIntent()
    data class SaveExperiment(val info: ExperimentInfo): HomeIntent()
}