package com.fynn.logbook.ui.recordlist

import com.fynn.logbook.base.BaseUiState
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState
import com.fynn.logbook.bean.RecordInfo
import com.fynn.logbook.repository.IAppRepostory
import com.fynn.logbook.util.showToast

class RecordListViewModel(private val repository: IAppRepostory): BaseViewMolder<RecordListState, RecordListIntent>() {
    override fun initUiState(): RecordListState {
        return RecordListState()
    }

    override fun handleIntent(intent: IUiEvent) {
        when(intent){
            is RecordListIntent.GetRecordListByExperimentId->
                getRecordListByExperimentId(intent.experimentId)
            is RecordListIntent.SaveRecordInfo->{
                val recordInfo = RecordInfo(mExperimentId = intent.experimentId, mRecordNum = intent.recordNum,
                mRoomNumber = intent.roomNum, mRecordWeight = intent.animalWeight)
                saveRecordInfo(recordInfo)
            }

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

    private fun saveRecordInfo( recordInfo: RecordInfo){
        scope {
            val result =  repository.saveRecord(recordInfo)
            if (result){
                showToast("保存成功")
                sendUiState {
                    copy(isFinishView = true)
                }
            }
        }
    }
}


data class RecordListState(
    val recordList:List<RecordInfo> = emptyList(),
    override val isFinishView:Boolean = false
):BaseUiState

sealed class RecordListIntent:IUiEvent{
    data class GetRecordListByExperimentId(val experimentId:Long):RecordListIntent()
    data class SaveRecordInfo(val experimentId: Long, val recordNum:String, val roomNum: String, val animalWeight: String):RecordListIntent()
}

