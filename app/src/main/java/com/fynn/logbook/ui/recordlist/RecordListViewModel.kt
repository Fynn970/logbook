package com.fynn.logbook.ui.recordlist

import com.fynn.logbook.base.BaseUiState
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState
import com.fynn.logbook.bean.ExperimentInfo
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
            is RecordListIntent.GetExperimentById->{
                getExperimentById(intent.id)
            }
            is RecordListIntent.UpdateExperiment->{
                updateExperiment(intent.experiment)
            }
        }
    }

    private fun updateExperiment(experiment: ExperimentInfo){
        scope {
            val result = repository.updateExperiment(experiment)
            if (result){
                sendDataShared { copy(experiment = experiment) }
                showToast("修改成功")
            }else{
                showToast("修改失败")
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

    private fun getExperimentById(id: Long){
        scope {
            val result = repository.getExperimentById(id)
            if (result != null) {
                sendDataShared { copy(experiment = result) }
            }else{
                showToast("获取失败，请稍后再试！")
            }
        }
    }
}


data class RecordListState(
    val recordList:List<RecordInfo> = emptyList(),
    val experiment: ExperimentInfo? = null,
    override val isFinishView:Boolean = false
):BaseUiState

sealed class RecordListIntent:IUiEvent{
    data class GetRecordListByExperimentId(val experimentId:Long):RecordListIntent()
    data class GetExperimentById(val id: Long): RecordListIntent()
    data class UpdateExperiment(val experiment: ExperimentInfo): RecordListIntent()
    data class SaveRecordInfo(val experimentId: Long, val recordNum:String, val roomNum: String, val animalWeight: String):RecordListIntent()
}

