package com.fynn.logbook.home

import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState
import com.fynn.logbook.bean.ExperimentInfo


data class HomeState(
//    val listState: ExperimentListUiState
        val list: List<ExperimentInfo> = emptyList(),
) : IUiState

//sealed class RecycleViewUiState {
//    object INIT : RecycleViewUiState()
//    data class SUCCESS(val models: MutableList<String>) : RecycleViewUiState()
//}

//sealed class ExperimentListUiState{
//    object INIT: ExperimentListUiState()
//    data class SUCCESS(val list: MutableList<ExperimentInfo>): ExperimentListUiState()
//}


sealed class HomeIntent : IUiEvent {
    object GetAllExperiment:HomeIntent()
    data class SaveExperiment(val info: ExperimentInfo): HomeIntent()
}


