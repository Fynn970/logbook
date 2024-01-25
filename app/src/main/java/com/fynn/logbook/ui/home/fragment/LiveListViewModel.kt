package com.fynn.logbook.ui.home.fragment

import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.repository.IAppRepostory


data class LiveListUiState(val list: List<ExperimentInfo> = emptyList(),): IUiState{

}

sealed class LiveListUiEvent:IUiEvent{
    data class GetAllLiveExperiment(val type: Int): LiveListUiEvent()
}


class LiveListViewModel(private val repostory: IAppRepostory) : BaseViewMolder<LiveListUiState, LiveListUiEvent>() {


    override fun initUiState(): LiveListUiState {
        return LiveListUiState()
    }

    override fun handleIntent(intent: IUiEvent) {
        when(intent){
            is LiveListUiEvent.GetAllLiveExperiment->{
                getAllLiveExperiment(intent.type)
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