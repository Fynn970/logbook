package com.fynn.logbook.ui.recordlist

import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState
import com.fynn.logbook.bean.RecordInfo
import com.fynn.logbook.repository.IAppRepostory

class RecordListViewModel(private val repository: IAppRepostory): BaseViewMolder<RecordListState, RecordListIntent>() {
    override fun initUiState(): RecordListState {
        return RecordListState()
    }

    override fun handleIntent(intent: IUiEvent) {
        when(intent){
            is RecordListIntent.GetRecordListByExperimentId->
                getRecordListByExperimentId(intent.experimentId)
        }
    }

    private fun getRecordListByExperimentId(experimentId: Long){
        scope {
            val recordList = repository.getRecordList(experimentId)
            sendUiState {
                copy(recordList = recordList)
            }

        }
    }
}


data class RecordListState(
    val recordList:List<RecordInfo> = emptyList()
):IUiState

sealed class RecordListIntent:IUiEvent{
    data class GetRecordListByExperimentId(val experimentId:Long):RecordListIntent()
}

