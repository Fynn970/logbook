package com.fynn.logbook.ui.home.fragment

import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.repository.IAppRepostory
import com.fynn.logbook.util.showToast


data class LiveListUiState(val list: List<ExperimentInfo> = emptyList(),): IUiState{

}

sealed class LiveListUiEvent:IUiEvent{
    data class GetAllLiveExperiment(val type: Int): LiveListUiEvent()
    data class DeleteExperimentById(val id:Long): LiveListUiEvent()
}


class LiveListViewModel(private val repostory: IAppRepostory) : BaseViewMolder<LiveListUiState, LiveListUiEvent>() {

    private var mViewType: Int = 0

    fun setViewType(viewType: Int)
    {
        mViewType = viewType
    }
    override fun initUiState(): LiveListUiState {
        return LiveListUiState()
    }

    override fun handleIntent(intent: IUiEvent) {
        when(intent){
            is LiveListUiEvent.GetAllLiveExperiment->{
                getAllLiveExperiment(intent.type)
            }
            is LiveListUiEvent.DeleteExperimentById->{
                deleteExperimentById(intent.id)
            }
        }
    }

    private fun deleteExperimentById(id:Long){
        scope {
            val result = repostory.deleteExperiment(id)
            if (result){
                getAllLiveExperiment(mViewType)
            }else{
                showToast("删除失败")
            }
        }
    }

    private fun getAllLiveExperiment(type: Int) {
        scope {
            val list = repostory.getExperimentList(type)
            sendUiState {
                copy(list = list)
            }
        }
    }

}